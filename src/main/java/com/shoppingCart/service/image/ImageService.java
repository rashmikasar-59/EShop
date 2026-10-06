package com.shoppingCart.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingCart.dto.ImageDto;
import com.shoppingCart.exceptions.ResourceNotFoundException;
import com.shoppingCart.model.Image;
import com.shoppingCart.model.Product;
import com.shoppingCart.repository.ImageRepository;
import com.shoppingCart.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService implements IImageService {
	private final ImageRepository imageRepository;
	private final IProductService productService;

	@Override
	public Image getImageById(Long id) {
		// TODO Auto-generated method stub
		return imageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No image found with this " + id));
	}

	@Override
	public void deleteImageById(Long id) {
		// TODO Auto-generated method stub
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
			throw new ResourceNotFoundException("No image found with this " + id);
		});
	}

	@Override
	public  List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		// TODO Auto-generated method stub
		Product product= productService.getProductById(productId);
		List<ImageDto> savedImageDto= new ArrayList<>();
		for(MultipartFile file:files)
		{
			try {
				Image image=new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				String buildDownloadUrl="/api/v1/images/image/download/";
				String downloadUrl= buildDownloadUrl+image.getId();
				image.setDownloadUrl(downloadUrl);
			Image savedImage=imageRepository.save(image);
			
			 savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
             imageRepository.save(savedImage);

             ImageDto imageDto = new ImageDto();
             imageDto.setId(savedImage.getId());
             imageDto.setFileName(savedImage.getFileName());
             imageDto.setDownloadUrl(savedImage.getDownloadUrl());
             savedImageDto.add(imageDto);

          }   catch(IOException | SQLException e){
              throw new RuntimeException(e.getMessage());
          }
      }
      return savedImageDto;
  }

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		// TODO Auto-generated method stub
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}

	}

}

