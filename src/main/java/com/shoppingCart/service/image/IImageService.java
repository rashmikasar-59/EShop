package com.shoppingCart.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingCart.model.Image;

public interface IImageService {

  Image	getImageById(Long id);
  void deleteImageById(Long id);
  Image saveImage(List<MultipartFile> filess,Long productId);
  void updateImage(MultipartFile file,Long imageId);
}
