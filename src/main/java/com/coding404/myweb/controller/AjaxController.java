package com.coding404.myweb.controller;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {

    @Value("${project.upload.path}")
    String uploadPath; //파일업로드 패스

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    //1단 카테고리
    @GetMapping("/getCategory")
    public ResponseEntity<List<CategoryVO>> getCategory() {
        ArrayList<CategoryVO> list = productService.getCategory();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //2단, 3단 카테고리
    @GetMapping("/getCategoryChild/{groupId}/{categoryLv}/{categoryDetailLv}")
    public ResponseEntity<List<CategoryVO>> getCategoryChild(@PathVariable("groupId") String groupId,
                                                             @PathVariable("categoryLv") int categoryLv,
                                                             @PathVariable("categoryDetailLv") int categoryDetailLv) {
        CategoryVO vo = CategoryVO.builder()
                .groupId(groupId)
                .categoryLv(categoryLv)
                .categoryDetailLv(categoryDetailLv)
                .build();
        ArrayList<CategoryVO> list = productService.getCategoryChild(vo);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    //이미지 데이터 응답에 대한 요청
//    @GetMapping("/display/{filepath}/{uuid}/{filename}")
//    public byte[] display(@PathVariable("filepath") String filepath,
//                          @PathVariable("uuid") String uuid,
//                          @PathVariable("filename") String filename) {
//        //하드디스크에 있는 파일 경로
//        byte[] result = null;
//        String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename; //파일의 실제 경로
//        File file = new File(path);
//
//        try {
//            result = FileCopyUtils.copyToByteArray(file); //파일데이터의 byte배열값을 구해서 반환
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result; //요청이 들어온 곳으로 응답.
//    }

    //ResponEntity를 이용해서 이미지 응답문서를 작성
    @GetMapping("/display/{filepath}/{uuid}/{filename}")
    public ResponseEntity<byte[]> display(@PathVariable("filepath") String filepath,
                                          @PathVariable("uuid") String uuid,
                                          @PathVariable("filename") String filename) {
        //하드디스크에 있는 파일 경로
        ResponseEntity<byte[]> result = null;

        String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename; //파일의 실제 경로
        File file = new File(path);

        try {

            byte[] arr = FileCopyUtils.copyToByteArray(file); //파일데이터의 byte배열값을 구해서 반환

            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", Files.probeContentType(file.toPath() )); //해당 경로 파일에 mime타입을 구함

            result = new ResponseEntity<>(arr, header, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result; //요청이 들어온 곳으로 응답.
    }


    //파일다운로드
    @GetMapping("/download/{filepath}/{uuid}/{filename}")
    public ResponseEntity<byte[]> download (@PathVariable("filepath") String filepath,
                                            @PathVariable("uuid") String uuid,
                                            @PathVariable("filename") String filename) {

        String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename; //파일의 실제 경로
        File file = new File(uploadPath);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Disposition", "attachment; filename=" + filename); //★컨텐츠 타입 지정

            byte[] fileCopy = FileCopyUtils.copyToByteArray(file); //바이너리 형식으로 불러오기
            result = new ResponseEntity<>(fileCopy, header, HttpStatus.OK); //바디에 담을내용, 헤더에 담을 내용, 상태메시지

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




}
