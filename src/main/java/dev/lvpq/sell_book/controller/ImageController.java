package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.entity.Image;
import dev.lvpq.sell_book.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
public class ImageController {
    ImageService imageService;

    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayImage(@PathVariable("id") String id) {
        try {
            Image image = imageService.getById(id);
            byte[] imageBytes = image.getContent().getBytes(1, (int) image.getContent().length());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (SQLException e) {
            log.error("Failed to retrieve image from database: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("listImageSample");
        List<Image> imageList = imageService.getAll();
        mv.addObject("imageList", imageList);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("/addImages");
    }

    @PostMapping("/add")
    public String addImagePost(@RequestParam("image") MultipartFile file)
            throws IOException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        Image image = new Image();
        image.setContent(blob);
        imageService.create(image);
        return "redirect:/";
    }
}
