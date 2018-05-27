package com.tira.booking.controllers;

import com.tira.booking.services.AdministratorService;
import com.tira.booking.utils.FileNameUtils;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

/**
 * The type Administrator controller.
 */
@Controller
public class AdministratorController extends BaseController {

	@Autowired
	private AdministratorService service;
	private static final String IMAGE_ASSETS_DIRECTORY = "./resources/static/images/";


	@RequestMapping(value = "/api/v1/admin/fileUpload", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity fileUpload(@RequestParam("file") MultipartFile picture,
									 @RequestParam("timestamp") String timestamp){
        return wrapForAdmin(() -> {
            if (picture != null && timestamp != null) {
            	String extension = FileNameUtils.getExtension(picture.getOriginalFilename());
                String pathString= IMAGE_ASSETS_DIRECTORY + timestamp + "."+ extension;
                byte[] bytes = picture.getBytes();
                Files.write(Paths.get(pathString),bytes);
                return "{ \"path\": \"" + "/images/"+ timestamp + "." +  extension+"\"}";
            } else {
                return "{ \"message\": \"" + "failed" + "\"}";
            }});
	}

    @RequestMapping(value = "/api/v1/admin/deletePicture/{pictureId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity deletePicture(@PathVariable String pictureId) {
		return wrapForAdmin(() -> this.service.deletePicture(UUID.fromString(pictureId)));
	}

	@RequestMapping(value = "/api/v1/admin/getAdministratorStatistics", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity getAdministratorStatistics() {
		return wrapForAdmin(() -> this.service.getAdministratorStatistics());
	}



}
