package com.shoppingCart.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingCart.dto.ImageDto;
import com.shoppingCart.model.Image;

public interface IImageService {

  Image	getImageById(Long id);
  void deleteImageById(Long id);
  List<ImageDto> saveImages(List<MultipartFile> filess,Long productId);
  void updateImage(MultipartFile file,Long imageId);
}
