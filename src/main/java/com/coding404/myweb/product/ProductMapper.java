package com.coding404.myweb.product;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ProductMapper {
    public int productInsert(ProductVO vo);//등록
    public ArrayList<ProductVO> getList(@Param("userId") String userId, @Param("cri") Criteria cri); //목록 // 매개변수가 2개 이상이면  , 이름을 붙여줘야 함
    public ProductVO getDetail(int prodId); //상세내역
    public int productUpdate(ProductVO vo); //수정
    public int productDelete(int prodId);
}
