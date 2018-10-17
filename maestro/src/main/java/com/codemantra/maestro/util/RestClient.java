package com.codemantra.maestro.util;

import org.springframework.web.client.RestTemplate;

import com.codemantra.maestro.response.UpdatePathRequest;

public class RestClient {

	/*public static void main(String[] params){
		updateJobPath();
	}*/
	
	private static void updateJobPath(){
		final String uri = "http://172.16.4.112:8088/maestro/updateJobPath";
		
		UpdatePathRequest request = new UpdatePathRequest();
		request.setJobId("9780815375818_Diva");
		request.setClientId("TF_STEM");
		request.setCopyEditPath("//172.16.1.2/Copyediting/05_Copyediting/2_T&F/T&F-PM/475_Wilson Lu - BIM and Big Data for Construction Cost Management/02_Copyediting/9780815390947_Wilson Lu/MaestroReady");
		request.setEquationsPath("//172.16.1.2/Copyediting/05_Copyediting/1_T&F CRC Press/Shoemaker_K43353/02_Copyediting/9781138558199_Shoemaker/MaestroReady/Equations");
		request.setGraphicsPath("//172.16.1.21/comp/GRAPHICS/T&F/T&F STEM/9781138558199_Shoemaker/FINAL");
		request.setTemplatePath("//172.16.1.4/comp_template/TEMPLATE/TandF_CRC/Projects/75_Yu_K24429/Saunders_ID9_A_P105_C1/02_cM Updated Template/IDML");
		request.setMappingPath("//172.16.1.21/comp_template/03_Maestro Settings/TF_CRC/Automation Root/Settings");
		request.setStyleSheetPath("//172.16.1.21/comp_template/03_Maestro Settings/01_Mapping Styles/TF_HSS_StylesNames.xlsx");
		
		RestTemplate rest = new RestTemplate();
		rest.put(uri, request);
	}
}
