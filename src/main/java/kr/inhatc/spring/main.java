package kr.inhatc.spring;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) throws IOException {
//		 String message= "서울 반포한강공원에서 숨진 채 발견된 손정민씨 사건과 관련해 일부 시민들이 손씨와 함께 있던 친구 A씨에 대한 거짓말탐지기 검사를 요구하고 있다. 네이버 카페 ‘반포한강사건 진실을 찾는 사람들(반진사)’ 회원들은 지난 1일 서울 종로구 서울경찰청 앞에서 A씨에 대한 거짓말탐지기 검사를 촉구하는 기자회견까지 열었다. A씨의 휴대전화를 습득한 환경미화원이 ‘인권침해라고 생각해 거짓말탐지기 검사를 거부했다’는 기사에는 ‘떳떳하게 검사를 받으라’ 등 추궁하는 댓글이 달렸다.\r\n"
//		            + "\r\n"
//		            + "참고인 신분으로 조사를 받고 있는 두 사람이 거짓말탐지기 검사를 꼭 받아야 할까, 그리고 그 검사에는 법적 효력이 있을까.\r\n"
//		            + "\r\n"
//		            + "경찰청 예규인 ‘거짓말탐지기 운영 규칙’에 따르면 거짓말탐지기 검사 대상은 피의자, 피내사자, 중요 참고인, 기타 수사 사항에 대하여 알고 있거나 관련되어 있다고 믿을 만한 상당한 이유가 있는 사람이다. 거짓말탐지기는 거짓말을 할 때 나타날 수 있는 호흡, 심장박동수, 혈압의 변화 등 생리적 변화를 측정해 진술의 진위를 가리는 장치다.\r\n"
//		            + "\r\n"
//		            + "다만 거짓말탐지기 검사는 피의자와 참고인 모두 거부할 수 있다. 거짓말탐지기 검사를 강제하는 것은 헌법에서 보장하는 진술거부권을 침해할 수 있기 때문이다. 경찰청 거짓말탐지기 운영 규칙 제3조(기본원칙)는 ‘거짓말탐지기 검사는 대상자가 동의를 한 경우에만 할 수 있다’고 명시하고 있다. 또 ‘검사를 거부한다는 이유로 불이익한 추정을 하거나 불이익한 결과를 초래할 조치를 할 수 없다’고 규정한다.\r\n"
//		            + "\r\n"
//		            + "거짓말탐지기 검사는 수사과정에서 참고자료로 활용될 수는 있지만 법적인 증거 능력은 없다. 거짓말탐지기 검사 결과를 100% 확신할 수 없기 때문이다. 대법원이 2005년 거짓말탐지기 검사의 증거능력을 부인하며 원심 판결을 뒤집고 피고인에게 무죄를 선고한 판례도 있다. 당시 피고인 A씨는 자동차를 운전하던 중 걸어가던 B씨를 치고 도주한 혐의로 기소됐다. A씨는 “사건 현장에 간 적도 없다”며 범행을 부인했다. 경찰이 실시한 거짓말탐지기 검사에서 A씨는 ‘당신이 사고를 내고 도주했습니까’ 등의 질문에 ‘아니오’라고 답했는데, 거짓 반응이 나왔다. 2심 재판부는 이 거짓말탐지기 검사 결과 등을 근거로 A씨에게 유죄를 선고했다.\r\n"
//		            + "\r\n"
//		            + "\r\n"
//		            + "\r\n"
//		            + "그러나 대법원은 “①거짓말을 하면 반드시 일정한 ‘심리상태의 변동’이 일어나고, ②그 심리상태의 변동은 반드시 일정한 ‘생리적 반응’을 일으키며, ③그 생리적 반응으로 피검사자의 말이 거짓인지 아닌지가 ‘정확히 판정’될 수 있어야 한다”며 “거짓말탐지기 검사가 위와 같은 세 가지 전제요건을 갖추었음을 인정할 만한 자료가 없으므로 증거능력이 없다”고 판결했다.\r\n"
//		            + "\r\n"
//		            + "\r\n"
//		            + "\r\n"
//		            + "원문보기:\r\n"
//		            + "http://news.khan.co.kr/kh_news/khan_art_view.html?artid=202106021321001&code=940100#csidxee74c5df760d479b1b3001e1a43cd54 ";
		String message = "신선 농산물과 양념 재료가 모두 들어있어 집에서 간편하게 음식을 만들어 먹을 수 있는 '밀키트' 제품이 인기를 끌고 있습니다.코로나19로 수요가 더 늘어나고 있는데요,지역 농산물로 제품을 만들다 보니 주변 농가들에도 큰 도움이 되고 있습니다. 오종우 기자의 보도입니다. '소고기 야채전골'을 해먹을 수 있는 재료와 양념이 모두 들어있는 이른바, 밀키트 제품입니다. 외식보다 저렴한 비용에 버섯과 청경채 같은 신선 농산물이 든 건강식을 먹을 수 있어 인기입니다. 밀양의 이 제조업체는 상품 출시 1년 만에 50억 원의 매출을 기록했습니다. 코로나19로 집밥 수요가 크게 늘어난 덕분입니다. [엄성민/밀양 밀키트 제조업체 : 저희가 손질이나 세척과정 없이 농산물 그대로를 사용하기 때문에 소비자분들이 원물의 신선도를 눈으로 확인하시고 섭취가 가능하십니다.] 밀양지역 농가들과 올해 맺은 계약재배 금액만 24억 원! 수확량의 절반가량은 계약재배를 한 덕분에 농민들은 가격 등락에 관계없이 농사에만 전념할 수 있습니다. [최광호/밀양 서홍감자 작목반 회장 : 1kg당 2천 원씩 계약을 해서, 일반 농가보다 한 동에, 하우스 한 동에 한 150만 원 정도 돈을 더 받았습니다. 밀키트에 들어가는 양배추와 상추, 깻잎 등 70여 가지 농산물을 밀양지역 농가에서 구매해 농민들 입장에선 든든한 거래처가 생긴 겁니다. [곽선칠/친환경농업인연합회 밀양시지부 회장 : 타지의 상인들보다도 지역 업체로서 아무래도 농민을 많이 생각하는 쪽에 서지 않나 생각합니다. 코로나19 여파로 인기를 끌고 있는 밀키트 제품이 어려움을 겪고 있는 주변 농가들에게 효자 노릇을 톡톡히 하고 있습니다. KBS 뉴스 오종우입니다.";
		// String message= " ";
		String command = "python test.py" + " " + message;

		Process child = Runtime.getRuntime().exec(command);


		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
		int c = 0;

		while ((c = in.read()) != -1) {

			System.out.print((char) c);
		}
		
		in.close();
	}

}
