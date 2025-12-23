package com.fashion.hunter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fashion.hunter.dto.SiteConfigDTO;

@Repository
public interface SiteConfigDAO {

	List<SiteConfigDTO.SiteConfigRowDTO> getSiteConfigRows(@Param("shopCodes") List<String> shopCodes);

	int updateSiteConfigDetails(SiteConfigDTO requestDTO) throws Exception;

	void insertSiteConfig(SiteConfigDTO siteConfigDTO) throws Exception;

	int deleteSiteConfigDetails(SiteConfigDTO requestDTO);

}