package com.api.core.appl.picture.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.api.core.appl.album.Album;
import com.api.core.appl.album.service.spec.AlbumService;
import com.api.core.appl.picture.Picture;
import com.api.core.appl.picture.PictureDTO;
import com.api.core.appl.picture.repository.spec.PictureRepository;
import com.api.core.appl.picture.service.spec.PictureService;
import com.api.core.appl.user.User;
import com.api.core.appl.user.service.spec.UserService;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;
import com.api.core.appl.util.UtilVariables;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class PictureServiceImpl implements PictureService {

	@Autowired
	PictureRepository pictureRepository;
	
	@Autowired
	UtilVariables utilVariables;
	
	@Autowired
	AlbumService albumService;
	
	@Autowired
	UserService userService;
	

	@Override
	public ArrayList<PictureDTO> listPicture(Filter filter) {

		Page<Picture> pagePicture;

		if (filter.getAlbumId() != null && filter.getPictureName() != null) {
			pagePicture = pictureRepository.listPictureByAlbumAndName(filter);
		}
		else if (filter.getAlbumId() != null) {
			pagePicture = pictureRepository.listPictureByAlbum(filter);
		} else if (filter.getPictureId() != null) {
			pagePicture = pictureRepository.findPictureByName(filter);
		} else {
			pagePicture = pictureRepository.listPicture(filter);
		}

		List<Picture> listaPicture = pagePicture.getContent();

		ArrayList<PictureDTO> listaPictureDTO = new ArrayList<>();
		

		StaticCredentialsProvider staticCredentialsProvider = utilVariables.getStaticCredentialsProvider();
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(staticCredentialsProvider)
            .build();
        
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);

		for (Picture picture : listaPicture) {
			byte[] pictureData = UtilLibrary.getObjectBytes(s3, utilVariables.getPicturesBucketName(), picture.getIdAmazonS3().toString());
			Instant instant = Instant.now();
	        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(picture.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			PictureDTO pictureDTO = new PictureDTO(picture.getId(), picture.getName(), localDateTime.format(formatter), pictureData, picture.getAlbum().getId());
			listaPictureDTO.add(pictureDTO);
		}

		return listaPictureDTO;
	}
	

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public PictureDTO createPicture(PictureDTO pictureDTO) {
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (pictureDTO.getDate() != null) {
			try {
				timestampDate = UtilLibrary.getDateTimestampF1(pictureDTO.getDate());
			}
			catch (DateTimeParseException e) {
				timestampDate = UtilLibrary.getDateTimestampF2(pictureDTO.getDate());
			}
		}
		

		UUID uuid = UUID.randomUUID();
		Picture picture = new Picture(pictureDTO.getName(), timestampDate, uuid.toString());
		
		Filter filter = new Filter();
		filter.setAlbumId(pictureDTO.getAlbumId());
		Album album = albumService.getAlbumEntity(filter);
		
		if(album.getId() == null) {
			filter.setUserId(1L); //TODO pegar o id da sessão do usuário
			User user = userService.getUserEntity(filter);
			album.setUser(user);
			album = albumService.createAlbumEntity(album);
		}
		
		picture.setAlbum(album);
		
		StaticCredentialsProvider staticCredentialsProvider = utilVariables.getStaticCredentialsProvider();
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(staticCredentialsProvider)
            .build();

		if(pictureDTO.getData() != null && pictureDTO.getData().length > 0) {
			UtilLibrary.putObjectBytes(s3, utilVariables.getPicturesBucketName(), picture.getIdAmazonS3().toString(), pictureDTO.getData());
		}
		
		picture = pictureRepository.createPicture(picture); 
		pictureDTO.setId(picture.getId());
		

		return pictureDTO;
	}


	@Override
	public PictureDTO getPicture(Filter filter) {
		
		Picture picture = pictureRepository.findPictureById(filter);
		
		if(picture.getId() == null) {
			PictureDTO pictureDTO = new PictureDTO(0L, "", "", null, 0L);
			return pictureDTO;
		}
		
		StaticCredentialsProvider staticCredentialsProvider = utilVariables.getStaticCredentialsProvider();
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(staticCredentialsProvider)
            .build();
        
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
        
        byte[] pictureData = UtilLibrary.getObjectBytes(s3, utilVariables.getPicturesBucketName(), picture.getIdAmazonS3().toString());
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(picture.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
        PictureDTO pictureDTO = new PictureDTO(picture.getId(), picture.getName(), localDateTime.format(formatter), pictureData,  picture.getAlbum().getId());
        
        return pictureDTO;
	}


	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public PictureDTO updatePicture(PictureDTO pictureDTO) {
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (pictureDTO.getDate() != null) {
			try {
				timestampDate = UtilLibrary.getDateTimestampF1(pictureDTO.getDate());
			}
			catch (DateTimeParseException e) {
				timestampDate = UtilLibrary.getDateTimestampF2(pictureDTO.getDate());
			}
		}
		

		UUID uuid = UUID.randomUUID();
		Picture picture = new Picture(pictureDTO.getName(), timestampDate, uuid.toString());
		
		Filter filter = new Filter();
		filter.setAlbumId(pictureDTO.getAlbumId());
		Album album = albumService.getAlbumEntity(filter);
		
		if(album.getId() == null) {
			filter.setUserId(1L); //TODO pegar o id da sessão do usuário
			User user = userService.getUserEntity(filter);
			album.setUser(user);
			album = albumService.createAlbumEntity(album);
		}
		
		picture.setAlbum(album);
		
		StaticCredentialsProvider staticCredentialsProvider = utilVariables.getStaticCredentialsProvider();
        Region region = Region.SA_EAST_1;
        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(staticCredentialsProvider)
            .build();

		if(pictureDTO.getData() != null && pictureDTO.getData().length > 0) {
			UtilLibrary.putObjectBytes(s3, utilVariables.getPicturesBucketName(), picture.getIdAmazonS3().toString(), pictureDTO.getData());
		}
		
		picture.setId(pictureDTO.getId());
		picture = pictureRepository.updatePicture(picture);

		return pictureDTO;
	}


	@Override
	public void deletePicture(Long pictureId) {
		pictureRepository.deletePicture(pictureId);
	}

}
