/*	var data; // json�곗씠��
	var text; // �먮Ц
	var pages;
	var pageIdx;
	var totalPageCnt;
	var incorrectArr = new Map();
	var length;
	
	$(function(){
		$(document).on("click", ".tableErrCorrect .nav a", function(){
			fApplyCandidate($(this));
		});
	});

	//援먯젙(�ㅻⅨ履�) �뚯씠釉� �앹꽦
	function makeHTML(idx){
		text =data[idx].str;
		var CorrectionTable ="<table style='border:0; cellpadding:0; cellspacing:0; width:411; height:480;'>";
		CorrectionTable +="<tbody>";
		CorrectionTable +="<tr>";
		CorrectionTable +="<td align='center' valign='center'><br>";
		length = data[idx].errInfo.length;
		

		for(var i=0;i<length;i++){
			var CorrectionSetId = data[idx].errInfo[i].errorIdx;
			var color = checkColor(data[idx].errInfo[i].correctMethod);
			
			CorrectionTable += "<table id='tableErr_"+ CorrectionSetId +"' class='tableErrCorrect' onclick='fShowSelText("+ CorrectionSetId +")' style='border:1; cellpadding:3; cellspacing:0;'>";
			CorrectionTable += "<tbody>";
			CorrectionTable += "<tr>";
			CorrectionTable += "<td class='tdLT' title='0'>�낅젰 �댁슜</td>";
			CorrectionTable += "<td id='tdErrorWord_"+ CorrectionSetId +"' class='tdErrWord'>";
			CorrectionTable += "<ul class='nav nav-stacked'><li><a href='javascript:void(0);' style='color: "+color+";'>"+data[idx].errInfo[i].orgStr+"</a></li>";
			CorrectionTable += "</ul></td>";
			CorrectionTable +="<tr>";
			CorrectionTable +="<td class='tdLT' style='vertical-align: top;'>��移섏뼱</td>";
			CorrectionTable +="<td id='tdReplaceWord_"+ CorrectionSetId +"' class='tdReplace'><ul class='nav nav-stacked'>";
			
			var replaceList = data[idx].errInfo[i].candWord;
			var replaceWord = replaceList.split('|');
			
            if(replaceList == ""){
            	CorrectionTable +="      <li><a href='javascript:void(0);'>��移섏뼱 �놁쓬</a></li>";
            } else{
    			for(var j in replaceWord){
   				 CorrectionTable +="      <li><a href='javascript:void(0);'>"+ replaceWord[j] +"</a></li>";
    			}            	
            }		
			
			CorrectionTable +="	</ul></td>";
			CorrectionTable +="</tr>";
			// 吏곸젒�섏젙�� �섏쨷�� �ｊ린.
 			CorrectionTable +="<tr>";
			CorrectionTable +="<td class='tdLT'>吏곸젒 �섏젙</td>";
			CorrectionTable +="<td class='tdReplace'><ul class='nav nav-stacked'>";
			CorrectionTable +="<li><textarea id='userReplace_"+ CorrectionSetId +"' class='tdUserReplace' onkeypress='if(event.keyCode==13){return false;}' placeholder='�먰븯�� ��移섏뼱瑜� 吏곸젒 �낅젰�섏꽭��.'></textarea>";
			CorrectionTable +="<button class='btnUserReplace' onclick='onUserReplace("+ CorrectionSetId +");'>�곸슜</button></li>";
			CorrectionTable +="</ul></td></tr>";		
			
			CorrectionTable +="<tr>";
			CorrectionTable +="<td class='tdLT'>�꾩�留�</td>";
			CorrectionTable +="<td id='tdHelp_"+ CorrectionSetId +"' class='tdETNor'><br>"+data[idx].errInfo[i].help+"</td>";
			CorrectionTable +="</tr>";

 			CorrectionTable +="<tr id='trBugReport_"+ CorrectionSetId +"'>";
			CorrectionTable +="<td class='tdLT'>�섍껄</td>";
			CorrectionTable +="<td id='tdBugReport_"+ CorrectionSetId +"' class='tdETNor'>";
			CorrectionTable +="<textarea id='comment_"+ CorrectionSetId +"' class='tdBugReport' style='width: 258px;' placeholder='��移섏뼱媛� 留욎� �딄굅��, �ㅻⅨ �섍껄�� �덉쑝硫� �댁슜�� �곸뼱�� [蹂대궡湲�]瑜� �뚮윭 二쇱꽭��.'></textarea>";
			CorrectionTable +="<button class='btnBugReport' onclick='onBugReport("+ CorrectionSetId +");'>蹂대궡湲�</button>";
			CorrectionTable +="</td></tr>";			
			CorrectionTable +="</tbody>";
			CorrectionTable +="</table>";
			CorrectionTable +="<br>"; 
		}
		
		CorrectionTable +="</td>";
		CorrectionTable +="</tr>";
		CorrectionTable +="</tbody>";
		CorrectionTable +="</table>";
		document.getElementById('divCorrectionTableBox1st').innerHTML = CorrectionTable;
		
		designText(idx);
		pageMoveBtn(idx);
		fRefreshResultTextLen();
		
		if(length == 0){
			setTimeout(function() {
				  alert('�꾩옱 �섏씠吏��� �ㅻ쪟媛� �놁뒿�덈떎.');
				}, 10);
		}
	}
	
	//�먮Ц(�쇱そ) �뚯씠釉� �뺣낫 �앹꽦
	function designText(idx){
		var designText = text;
		
		for(var i=data[idx].errInfo.length-1;i>=0;i--){
			var errorIdx = data[idx].errInfo[i].errorIdx;
			var start = data[idx].errInfo[i].start;
			var end = data[idx].errInfo[i].end;
			var color = checkColor(data[idx].errInfo[i].correctMethod);
			//console.log("nCorrectMethod = " + data[idx].errInfo[i].correctMethod);
			var replace = "<font id='ul_"+errorIdx+"' color='"+color+"' class='ul' onclick='fShowHelp("+errorIdx+")'>"+data[idx].errInfo[i].orgStr+"</font>";
			designText = designText.substr(0,start) + replace + designText.substr(end,designText.length);
		}
		document.getElementById('tdCorrection1stBox').innerHTML = designText;
		redesign();
	}
	
	function checkColor(num){

		if(num == 2){
			return 'red';
		} else if(num == 3){
			return 'lightbrown';
		} else if(num == 4){
			return 'green';
		} else if(num == 6){
			return 'darkmagenta';
		} 
		
		return 'blue';
	}
	
	//�ㅻⅨ履� �뚯씠釉붿뿉�� ��移섏뼱瑜� �대┃�덉쓣 ��.
	function fApplyCandidate(obj) {
	    var objLI = obj.parent();
	    var objUL = objLI.parent();
	    var objTD = objUL.parent();
	    var objTR = objTD.parent();
	    var objTable = objTR.parent();
	    var tdid = obj.closest('td').attr('id');
	    var a = document.getElementById(tdid);

	    var strDigitID = fGetStrDigitID(objTD[0].id.toString());
	    var errText = $('#tdErrorWord_' + strDigitID).text();
	    var list = $('#tdReplaceWord_' + strDigitID).find('li');

//	    var str="";
//		if(errText != objLI.text()){	
//			incorrectArr.delete(errText);
//			if(list.length>2){
//				if(list.eq(0).text() != objLI.text()){
//					for(var i=0;i<list.length;i++){
//						str += list.eq(i).text();
//						if(i<(list.length-1)){
//							str+='|';
//						}
//					}
//					clickReplace(errText, objLI.text(),str);					
//				}
//			}
//		}	
		
	    objTable.find('li').removeClass('selectedCand');
	    objLI.addClass('selectedCand');

	    $('#ul_' + strDigitID).html(objLI.text());
	    $('#ul_' + strDigitID).css('color', 'black');

	    fRefreshResultTextLen();
	}

	function nextButtonClick(){
		fShowLoadingAniPopup(350, 300, 306, 134, true);
		document.getElementById('nextInputForm').submit();
	}
	
	//��移섏뼱瑜� �대┃�덉쓣 ��, �곗씠�� �꾩넚.
	function clickReplace(errorWord, replaceWord, wordList) {
//		sentence = makeSentence(errorWord);
//		
//		var jsonArr = new Array();
//		
//		var data = new Object();
//		
//		data.errorWord = errorWord;
//		data.replaceWord = replaceWord;
//		data.wordList = wordList;
//		data.sentence = sentence;
//
//		jsonArr.push(data);
//		
//       	$.ajax({
//    		type : 'post',
//    		url : '/speller/results/clickReplace',
//    		contentType: 'application/json',
//    		data : JSON.stringify(jsonArr),
//    		error : onError,
//    		success : onSuccess
//    	});    
		
	}
	
	//踰꾧렇由ы룷�� �곗씠�� �꾩넚
	function onBugReport(idx){
		
		var errorWord = $('#tdErrorWord_' + idx).find('li').eq(0).text();
		var sentence = makeSentence(errorWord);
		var comment = $('#comment_' + idx).val();
		
		
		if(!comment=="" || !comment==null){
			var replaceList= $('#tdReplaceWord_' + idx).find('li');
	
			var replaceWord = '';
			for(var i=0;i<replaceList.length; i++){
				replaceWord += replaceList.eq(i).text();
				
				if(i<(replaceList.length-1)){
					replaceWord +='<br>';
				}
			}
	
			var jsonArr = new Array();
			
			var data = new Object();
			
			data.strTitle = comment;
			data.inputStr = text;		
			data.errorWord = errorWord;
			data.replaceWord = replaceWord;
			data.comment = comment;
	
			jsonArr.push(data);
			
	       	$.ajax({
	    		type : 'post',
	    		url : '/results/bugreport',
	    		contentType: 'application/json',
	    		data : JSON.stringify(jsonArr),
	    		error : onError,
	    		success : function (){
	    			document.getElementById('tdBugReport_' + idx).innerHTML = '<CENTER>蹂대궡湲� �꾨즺</CENTER>';
	    		}
	    	});  
		} else { 
			alert("�댁슜�� �낅젰�댁＜�몄슂.");
		}
		
	}
	
	//�ъ슜�먭� ��移섏뼱瑜� 吏곸젒 �섏젙�� �곗씠�� �꾩넚
	function onUserReplace(idx){
		
		var errorWord = $('#tdErrorWord_' + idx).text();
		var replaceWord = $('#userReplace_' + idx).val();
		var sentence;
		
		
	    $('#ul_' + idx).html(replaceWord);
	    $('#ul_' + idx).css('color', 'black');
	    
	    $('#tableErr_' + idx).find('li').removeClass('selectedCand');
	    
	    fRefreshResultTextLen();
	    
        if (errorWord != replaceWord) {
            if(errorWord !=" " || replaceWord !=""){
               sentence = makeSentence(errorWord);
               
	           	$.ajax({
	        		type : 'post',
	        		url : '/results/userReplace',
	        		contentType: 'application/json',
	        		data : JSON.stringify(makeJSON(errorWord, replaceWord, sentence)),
	        		error : onError,
	        		success : onSuccess
	        	});   
            }
        }
    
	}
	
	//�섏젙�섏� �딆� �ㅻ쪟 �곗씠�� �꾩넚
	function inputIncorrect(){
//		var lastArrSize = incorrectArr.size;
//		var jsonArr = new Array();
//		var sentence;
//		if(incorrectArrSize != lastArrSize) {
//			incorrectArr.forEach(function(value, key) {
//				
//				sentence = makeSentence(key);
//				
//				var data = new Object();
//				
//				data.errorWord = key;
//				data.replaceWord = value;
//				data.sentence = sentence;
//				
//				jsonArr.push(data);
//			});
//
//           	$.ajax({
//        		type : 'post',
//        		url : '/speller/results/notChange',
//        		contentType: 'application/json',
//        		data : JSON.stringify(jsonArr),
//        		error : onError,
//        		success : onSuccess
//        	});
//		}
	}
	//DB�� ���ν븯湲� �꾪븳 JSON �곗씠�� �앹꽦
	function makeJSON(errorWord, replaceWord, sentence){
		var jsonArr = new Array();
		
		var data = new Object();
		
		data.errorWord = errorWord;
		data.replaceWord = replaceWord;
		data.sentence = sentence;

		jsonArr.push(data);
		
		return jsonArr;
	}
	
	//�뺣뮘 4�댁젅�⑺빐�� 臾몄옣 留뚮벉
	function makeSentence(errorWord) {

		var delTag = text.replace(/(<([^>]+)>)/ig, "");
		// console.log(delTag);

		var pos = delTag.indexOf(errorWord);
		// console.log("errWord �꾩튂 : " + pos);
		var cnt = 0;
		var startPos = 0, endPos = 0;
		for (var i = pos; i > 0; i--) {
			var chk = delTag.charAt(i);
			if (chk == ' ') {
				cnt += 1;
			}
			if (cnt == 4) {
				startPos = i;
				break;
			}
		}
		cnt = 0;
		var endStart = pos + errorWord.length;
		for (var i = endStart; i <= delTag.length; i++) {
			var chk = delTag.charAt(i);
			if (chk == ' ') {
				cnt += 1;
			}
			if (cnt == 4) {
				endPos = i;
				break;
			}
			endPos=i;
		}
		var sentence = delTag.substring(startPos, endPos).toString();
		sentence = sentence.replace(/(\r\n\t|\n|\r\t)/gm,"");
		if(sentence.length == 0){
			sentence = delTag;
		}
		
		return sentence;
	}
	
	//ajax �먮윭 濡쒓렇
	function onError() {
		//console.log("ajax fail")
	}
	
	//ajax �깃났 濡쒓렇
	function onSuccess(data, status) {
		//console.log("�깃났");
	}
	
	//�뚯븘媛�湲�
	function fRenew() {
		document.location.href="http://speller.cs.pusan.ac.kr";
		
	}
	
	function back(){
		var back = (pageIdx+1) * -1;
		//console.log(back);
		if(back == 0){
			history.back();
		} else {
			history.go(back);
		}
		document.getElementById("divPopupBackground").style.visibility = "hidden";
		document.getElementById("divPopupBackground").style.display = "none";
		document.getElementById("imgPopup").style.visibility = "hidden";
	    document.getElementById("imgPopup").style.display = "none";

	}
	
	//�ㅻ쪟�⑥뼱 �꾩뿉 ��移섏뼱 �앹꽦.
	function redesign() {
	    $("#tdCorrection1stBox .ul").each(function () {
	        var word = $(this);
	        
	        var id = $(word).attr("id");
	        var color = $(word).attr("color");
	        word.addClass(color);
	        var id_no = id.replace("ul_", "");
	        var correct_id = "tdReplaceWord_" + id_no;
	        var correct_item = $("#" + correct_id);
	        var correct_word = $("li:nth-child(1) a", correct_item).text(); // ��移섏뼱 紐⑸줉 以� �� �꾩뿉 �덈뒗 寃껋쓣 ��移섏뼱濡� �쒖떆.
	        if (correct_word == "��移섏뼱 �놁쓬") correct_word = "?";
	        word.prepend($("<span class=correction>" + correct_word + "</span>"));

	        check_minor_change($(this));

	        word.on("mouseover", function () {
	            var offset = $("#tableErr_" + id_no).offset().top - 136 + $("#divCorrectionTableBox1st").scrollTop();
	            $("#divCorrectionTableBox1st").scrollTop(offset);
	        });
	    });

	    $(".correction").bind('click', {}, function (event) {
	        replace_correction(this);
	        event.stopPropagation();
	    });

	    $(".tdErrWord").each(function () {
	        var word = $(this);
	        var id = $(word).attr("id");
	        var id_no = id.replace("tdErrorWord_", "");
	        var correct_id = "ul_" + id_no;

			//�ㅻ쪟紐⑸줉 ����
			var incorrect_id = "tdErrorWord_" + id_no;
			var incorrect_item = $("#" + incorrect_id);
			var incorrect_word = $("li:nth-child(1) a", incorrect_item).text();

	        var correct_id = "tdReplaceWord_" + id_no;
	        var correct_item = $("#" + correct_id);
			var listLi= correct_item.find('li');

			var str = '';
			for(var i=0;i<listLi.length; i++){
				str += listLi.eq(i).text();
				
				if(i<(listLi.length-1)){
					str+='|';
				}
			}

			incorrectArr.set(incorrect_word,str);
	        var color = $("#" + correct_id).attr("color");
	        var parent = $(this).parents("table").eq(0);
	        $(".tdReplace", parent).addClass(color);
	    });
		
		incorrectArrSize = incorrectArr.size;
	    $(".btnBugReport").each(function () {
	        var word = $(this);
	        word.addClass("btnBugReport2");
	        word.removeClass("btnBugReport");
	    });

	    g_idxTableForScroll_1 = 0;
	}

	String.prototype.unescapeHtml = function(){
		  return this.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, "\"");
		};

	//�섏씠吏� �대룞踰꾪듉 �앹꽦
	function pageMoveBtn(idx){
		var prev;
		var next;
		var page;
		
		if(totalPageCnt != 1){
			if(pageIdx == 0){
				prev = "<input id='btnPrev' type='image' tabindex='4' title='泥� 寃곌낵�낅땲��.' src='./images/btnPrev_disable.gif' style='cursor: default;''>";
					
			}else {
				page = idx-1;
				prev = "<input id='btnPrev' "; 
				prev += "type='image' tabindex='4' title='�댁쟾 寃곌낵' "; 
				prev += "src='./images/btnPrev.gif' "; 
				prev += "onmouseout='this.src=\"./images/btnPrev.gif\";' "; 
				prev += "onmouseover='this.src=\"./images/btnPrev_over.gif\";' "; 
				prev += "onmousedown='this.src=\"./images/btnPrev_click.gif\";' "; 
				prev += "onclick='history.back(-1);'>";
			}
			if(document.getElementById("text1").value == null || document.getElementById("text1").value == ""){
				next = "<input id='btnNext' type='image' tabindex='5' title='留덉�留� 寃곌낵�낅땲��.' src='./images/btnNext_disable.gif' style='cursor: default;'>";		
			}else{
				page = idx+1;
				next = "<input id='btnNext' ";
				next += "type='image' tabindex='5' title='�ㅼ쓬 寃곌낵' ";
				next += "src='./images/btnNext.gif' ";
				next += "onmouseout='this.src=\"./images/btnNext.gif\";' ";
				next += "onmouseover='this.src=\"./images/btnNext_over.gif\";' ";
				next += "onmousedown='this.src=\"./images/btnNext_click.gif\";' ";
				next += "onclick='nextButtonClick()'>";
			}
			document.getElementById('prevBtn').innerHTML=prev;
			document.getElementById('nextBtn').innerHTML=next;
		} 
		
	}
	
	function check_minor_change(word) {
	    var correction = $(".correction", word);
	    var correction_word = correction.text();
	    var org_word = word.clone().find(".correction").remove().end().text()
		
	    if (correction_word.replace(/[^a-zA-Z0-9媛�-��]/ig, "") == org_word.replace(/[^a-zA-Z0-9媛�-��]/ig, "")) {
	        correction.addClass("minor");
	        var c2 = "";
	        var j = 0;
	        for (var i = 0; i < correction_word.length; i++) {
	            var current_char = correction_word.charAt(i);

	            if (current_char != org_word.charAt(j)) {
	                if (current_char == " ") {
	                    c2 += "<span class=sp></span>" + current_char;
	                    j--;
	                }
	                else if (org_word.charAt(j) == " " && current_char == org_word.charAt(j + 1)) {
	                    c2 += "<span class=tie></span>";
	                    i--;
	                }
	                else if (current_char.match(/[^a-zA-Z0-9媛�-�� ]/)) {
	                    c2 += "<span class=puct>" + current_char + "</span>";
	                    j--;
	                }
	                else if (org_word.charAt(j) != " " && current_char == org_word.charAt(j + 1)) {
	                    i--;
	                }
	                else if (current_char.match(/[a-zA-Z0-9媛�-��]/) && org_word.charAt(j).match(/[^a-zA-Z0-9媛�-��]/)) {
	                    i--;
	                }

	            } else {
	                c2 += current_char;
	            }
	            j++;
	        }
	        correction.html(c2);
	    }
	}

	function check_same(id, check_word) {

	    $(".ul").each(function () {
	        if ($(this).attr("id") == id) {
	            return;
	        }
	        if ($(this).text() == check_word) {
	            replace_correction($(".correction", this)[0]);
	            return false;
	        }
	    });
	}
	
	function fShowHelp(idx) {
		// do nothing
	    var obj = $("#ul_" + idx + " .correction");
	    replace_correction(obj);
	}
	
	function replace_correction(obj) {
	    var parent = $(obj).parent();
	    var strText = $(obj).text();
	    if (!parent.hasClass("ul")) return;

	    var check_word = parent.text();
	    var repLen = strText.length;
	    var errText = check_word.substr(repLen, check_word.length);

	    if(errText != strText){
			incorrectArr.delete(errText);
	    }
	    parent.animate({
	            color: "#fff"
	        },
	        150,
	        function () {
	        });

	    $(obj).animate({
	            marginTop: 0
	        },
	        300,
	        function () {
	            // Animation complete.
	            parent.html(strText);
	            parent.css('color', "inherit");
	            parent.addClass("done");
	            check_same($(parent).attr("id"), check_word);

	        });
	    fRefreshResultTextLen();
	}
	
	//湲��� �� 泥댄겕
	function fRefreshResultTextLen() {
		textOrgLen =$('#tdCorrection1stBox').clone().find(".correction").remove().end().text().length;
	    $('#tResultLTitle1').text('[珥� ' + fMakeCommaNum(textOrgLen) + '��]');
	}
	
	
	//蹂듭궗�섍린 湲곕뒫
	function fAddClickEventToCopy() {

        var copyText = $('#tdCorrection1stBox').clone().find(".correction").remove().end().text();
        var textArea = document.createElement("textarea");
        textArea.value = copyText;
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();

        try {
            var successful = document.execCommand('copy');
            var msg = successful ? 'successful' : 'unsuccessful';
        } catch (err) {
        }

        document.body.removeChild(textArea);
		
		inputIncorrect();
        toast("蹂듭궗媛� �꾨즺�섏뿀�듬땲��.");

	}
	
	function toast(msg) {
	    $("<div class='ui-loader ui-overlay-shadow ui-body-e ui-corner-all'><h3>" + msg + "</h3></div>")
	        .css({
	            display: "block",
	            opacity: 0.90,
	            position: "absolute",
	            "z-index": "120",
	            padding: "7px",
	            "text-align": "center",
	            width: "270px",
	            left: ($(window).width() - 284) / 2,
	            top: $(window).height() * 1.5
	        })

	        .appendTo($("body"))
	        .animate({
	            top: $(window).height() * 0.4
	        }, 300, function () {
	        })

	        .delay(2500)
	        .animate({
	            top: $(window).height() * 1.5
	        }, 300, function () {
	            $(this).remove();
	        });
	}
	
	// �뚯씠釉� �대┃ �� �� 蹂�寃�
	function fShowSelText(idx) {
	    fClearTableHilight(false);
	    fTableHilight(idx, '#ffffcc');
	    fClearULHilight();
	    fULHilight(idx);
	    g_ScrollAniDepth = 0;
	    g_idxWordForScroll = idx;

	    var forLeft1 = ((idx == "" || idx < 1000) ? true : false);
	    if (forLeft1) g_idxTableForScroll_1 = idx;
	    else g_idxTableForScroll_2 = idx;
	    g_idxTableForScroll = idx;

        fTextScrollAni();
	}
	
	function fTableHilight(idx, color) {
	    var idUL = "tableErr_" + idx;
	    var fontUL = document.getElementById(idUL);
	    fontUL.style.backgroundColor = color;
	}
	
	function fULHilight(idx) {
	    var idUL = "ul_" + idx;
	    var fontUL = document.getElementById(idUL);
	    fontUL.style.backgroundColor = 'rgb(255, 255, 204)';
	}
	
	function fClearTableHilight(bIfNoneSel) {
	    var bgColor = document.getElementById('tdCorrection1stBox').style.backgroundColor;

	    var i;
	    for (i = 0; i < length; i++) {
	        if (bIfNoneSel && g_idxTableForScroll == i) continue;
	        var idUL = "tableErr_" + i;
	        var fontUL = document.getElementById(idUL);
	        if (fontUL == null) continue;
	        fontUL.style.backgroundColor = bgColor;
	    }
	}
	
	function fClearULHilight() {
	    var bgColor = document.getElementById('tdCorrection1stBox').style.backgroundColor;

	    var i;
	    for (i = 0; i < length; i++) {
	        var idUL = "ul_" + i;
	        var fontUL = document.getElementById(idUL);
	        if (fontUL == null) continue;
	        fontUL.style.backgroundColor = bgColor;
	    }
	}
	
	function fTextScrollAni() {
	    g_ScrollAniDepth++;

	    var s1;
	    if (g_idxWordForScroll < 1000) s1 = document.getElementById('divLeft1');
	    else s1 = document.getElementById('divLeft2');

	    var halfBound = fPixelToNum(s1.clientHeight) / 2;	// 李� 以묎컙�� 蹂댁씠寃�
	    var ul = document.getElementById("ul_" + g_idxWordForScroll);
	    var offTopTable = Math.min(Math.max(ul.offsetTop - halfBound, 0), s1.scrollHeight - s1.clientHeight);


	    var oldScrollTop = s1.scrollTop;
	    var maxGap = 0;
	    if (g_ScrollAniDepth < 7) {
	        maxGap = Math.abs(fComeCloseScrollTopS(s1, offTopTable, g_ScrollAniDepth * 20, g_ScrollAniDepth * 20, halfBound));
	        if (maxGap > halfBound && oldScrollTop == s1.scrollTop)
	            maxGap = Math.abs(fComeCloseScrollTop(s1, offTopTable, 0.5, 4, halfBound));
	    }
	    else
	        maxGap = Math.abs(fComeCloseScrollTop(s1, offTopTable, 0.5, 4, halfBound));

	    if (g_ScrollAniDepth > 100 || maxGap < 4) {
	        g_ScrollAniDepth = 0;
	        s1.scrollTop = offTopTable;

	        return;
	    }

	    if (maxGap < 50) setTimeout("fTextScrollAni();", 100);
	    else setTimeout("fTextScrollAni();", 20);
	}
	
	function fComeCloseScrollTop(element, to, _nRatio, _nThreshold, _nTargetHeight) {
	    var obj = element.scrollTop;
	    var newPos;

	    if (obj == to) return 0;
	    if (obj < to) newPos = obj + (to - obj) * _nRatio;
	    else newPos = obj - (obj - to) * _nRatio;

	    if (Math.abs(to - obj) < _nThreshold) return to - obj;


	    element.scrollTop = newPos;
	    return to - newPos;
	}
	
	function fComeCloseScrollTopS(element, to, _step, _nThreshold, _nTargetHeight) {
	    var obj = element.scrollTop;
	    var newPos;

	    if (obj == to) return 0;
	    if (obj < to) newPos = obj + _step;
	    else newPos = obj - _step;

	    if (Math.abs(to - newPos) < _nThreshold) return to - obj;

	    element.scrollTop = newPos;
	    return to - newPos;
	}	
	
	function fPixelToNum(pixel) {
	    if (typeof pixel != "string")
	        return pixel;

	    pixel = pixel.replace("px", "");
	    pixel = pixel.replace("PX", "");
	    return Number(pixel);
	}
	
	function fScroll1() {
	    return;

	    if (g_idxSCrol == 2) return;
	    g_idxSCrol = 1;

	    var s1 = document.getElementById('divLeft1');
	    var s2 = document.getElementById('divLeft2');

	    var posRat1 = parseFloat(s1.scrollTop) / parseFloat(s1.scrollHeight);
	    var pos2 = parseInt(posRat1 * parseFloat(s2.scrollHeight));

	    if (s2.scrollTop != pos2) s2.scrollTop = pos2;

	    var t = setTimeout("g_idxSCrol=0;", 500);
	}
	
	function fMakeCommaNum(n) {
	    return (!n || n == Infinity || n == 'NaN') ? 0 : String(n).replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}
	
	function fGetStrDigitID(strID) {
	    var posUL = strID.lastIndexOf('_');
	    if (0 > posUL) return -1;

	    var strDigitID = strID.substring(posUL + 1, strID.length);

	    var nDigitID = parseInt(strDigitID);
	    if (isNaN(nDigitID) || 0 > nDigitID) {
	        return -1;
	    }

	    return strDigitID;
	}
	
	
	function fShowHelpPopup(strPath, x, y, w, h) {
	    fShowImgPopup(3, 83, 844, 559, strPath + "images/WebSpellerHelp_newUI.jpg", true, true);
	}
	
	function fShowFramePopup(x, y, w, h, strPath) {
	    fShowPopupBackground();
	    fShowFrameElement("framePopup", x, y, w, h, strPath);
	    fShowCloseBtn(x, y, w);
	}

	function fShowImgLoading(x, y, w, h, strPath, bWithBackround, bShowCloseButton) {
	    fShowFrameElement("imgLoading", x, y, w, h, strPath);
	    if (bWithBackround != false){
		    fShowPopupBackground();
	    }
	        
	    if (bShowCloseButton != false)
	        fShowCloseBtn(x, y, w);
	}
	
	function fShowImgPopup(x, y, w, h, strPath, bWithBackround, bShowCloseButton) {
	    fShowFrameElement("imgPopup", x, y, w, h, strPath);
	    if (bWithBackround != false)
		    fShowPopupBackground();
	    if (bShowCloseButton != false)
	        fShowCloseBtn(x, y, w);
	}
	
	function fFirstSpell() {
	    
		
		if(document.getElementById('text1').value != ""){
			fShowLoadingAniPopup(350, 300, 306, 134, true);
		    var nextText = fClearGarbageBlank(document.getElementById('text1').value);
		    document.getElementById('text1').value = nextText;
		    document.inputForm.submit();			
		} else{
			alert("寃��ы븷 臾몄옣�� �낅젰�섏꽭��.");
		}
	}
	
	function fShowFrameElement(elementID, x, y, w, h, strPath) {

	    var element = document.getElementById(elementID);

	    element.src = strPath;
	    element.style.visibility = "visible";
	    
	    element.style.top = y + "px";
	    element.style.left = x + "px";
	    element.style.width = w + "px";
	    element.style.height = h + "px";
	}
	
	function fClearGarbageBlank(inputText) {
	    var newTable = new String(inputText);
	    var newTable2 = new String("");

	    while (newTable != newTable2) {
	        newTable2 = newTable;
	        newTable = newTable.replace(" \n", "\n");
	        newTable = newTable.replace(" \r", "\r");
	        newTable = newTable.replace("\n  ", "\n ");
	        newTable = newTable.replace("\r  ", "\r ");

	        newTable = newTable.replace("\t\n", "\n");
	        newTable = newTable.replace("\t\r", "\r");
	        newTable = newTable.replace("\n\t\t", "\n\t");
	        newTable = newTable.replace("\r\t\t", "\r\t");
	    }

	    return newTable2;
	}
	
	function fShowPopupBackground() {
	    var tableMain = document.getElementById("tableMain");
	    var background = document.getElementById("divPopupBackground");
	    background.style.left = tableMain.style.left;
	    background.style.top = tableMain.style.top;
	    background.style.width = tableMain.style.width;
	    background.style.height = tableMain.style.height;
	    background.style.opacity = '0.0';
	    background.style.visibility = "visible";
	    background.style.display = '';
	}

	function fShowCloseBtn(x, y, w) {
	    var tableClose = document.getElementById("divClosePopup");
	    tableClose.style.top = y + 20 + "px";//-25;
	    //tableClose.style.left = x+w - 30 + "px";
	    tableClose.style.left = x + w - 150 + "px";
	    tableClose.style.visibility = "visible";
	}
	
	function fShowUserReportPopup(strPath) {
	    fShowFramePopup(0, 80, 850, 565, strPath + "/UserReport");
	}
	
	function isEnter(key) {
	    keyValue = (navigator.appName == 'Netscape') ? key.which : key.keyCode;
	    if (keyValue == 13 && key.ctrlKey
	        || keyValue == 13 && key.shiftKey
	    ) {
			
	        inputForm.submit();
	    }
	    else if (keyValue == 13) {
			//console.log("�뷀꽣");
	    }
	}
	//硫붿씤�붾㈃ 湲��� �� 泥댄겕
	function fRefreshTextLen() {
	    textLen = $('#text1').val().length;
	    $('#divTextLen').text('[珥� ' + fMakeCommaNum(textLen) + '��]');
	}
	
	function fShowOrderPopup(strPath) {
	    fShowFramePopup(0, 80, 850, 565, strPath + "/SpellerOrder");
	}
	
	function fShowFramePopup(x, y, w, h, strPath) {
	    fShowPopupBackground();
	    fShowFrameElement("framePopup", x, y, w, h, strPath);
	    fShowCloseBtn(x, y, w);
	}
	
	function fHidePopup() {
	    document.getElementById("divPopupBackground").style.visibility = "hidden";
	    document.getElementById("framePopup").style.visibility = "hidden";
	    document.getElementById("imgPopup").style.visibility = "hidden";
	    document.getElementById("divClosePopup").style.visibility = "hidden";
	}
	
	function fShowLoadingAniPopup(x, y, w, h, bWithBackround) {
		fShowImgLoading(x, y, w, h, './images/loadingAnimation.gif', bWithBackround, false);
	}
	
	//�섍껄 蹂대궡湲�
	function onReportOth() {
	    var strComment = new String(document.getElementById('txtCommentOth').value);

	    if (strComment == "�뚯쨷�� �섍껄 怨좊쭥寃� 諛쏄쿋�듬땲��.") {
	        alert("�섍껄�� �낅젰�� 二쇱꽭��.");
	        return;
	    }
	    if (strComment == "") {
	        alert("�섍껄�� �낅젰�� 二쇱꽭��.");
	        return;
	    }
	    
       	$.ajax({
    		type : 'post',
    		url : '/speller/SendMail',
    		data : {
    			content:strComment
    			},
    		error : function(){
    			alert("蹂대궡湲� �ㅽ뙣");
    		},
    		success: function(data){
    			alert("蹂대궡湲� �깃났");
    		}
    	}); 	
	}
	
	// 荑좏궎 媛��몄삤湲�
	function getCookie(cName) {
		cName = cName + '=';
		var cookieData = document.cookie;
		var start = cookieData.indexOf(cName);
		var cValue = '';
		if(start != -1){
			start += cName.length;
			var end = cookieData.indexOf(';', start);
			if(end == -1)end = cookieData.length;
			cValue = cookieData.substring(start, end);
		}
		return unescape(cValue);
	}
	
	function fClickCheckBoxModeChange()
	{
		if( $("input:checkbox[id='btnModeChange']").is(":checked") )
		{
			setCookie('ruleMode','strong',7);
		}
		else
		{
			setCookie('ruleMode','weak',7);
		}
	}
	function fClickTextModeChange()
	{
		if( $("input:checkbox[id='btnModeChange']").is(":checked") )
		{
			$("#btnModeChange").prop("checked", false);
			setCookie('ruleMode','strong',7);
		}
		else
		{
			$("#btnModeChange").prop("checked", true);
			setCookie('ruleMode','weak',7);
		}
	}
	
	function fSetModeOptOnLoad()
	{
		if( getCookie('ruleMode') == 'strong' )
		{
			$("#btnModeChange").prop("checked", true);
			setCookie('ruleMode','strong',7);
		}
		else
		{
			$("#btnModeChange").prop("checked", false);
			setCookie('ruleMode','weak',7);
		}
	}
	
////////////////////////////////////////////////////////////////////
	// for Popup

	function setCookie(name, value, expiredays) {
	    var todayDate = new Date();
	    todayDate.setDate(todayDate.getDate() + expiredays);
	    document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
	}

	function fClosePopupMsg() {
	    if (document.formPopup.chkTimer.checked) {
	        setCookie("CloseSpellerPopupMsg", "done", 1);
	    }
	    document.all['divPopupMsg'].style.visibility = "hidden";
	    document.all['divPopupMsg'].innerHTML = "";
	}

	function fShowPopupMsgIfFirst() {
	    cookiedata = document.cookie;
	    if (cookiedata.indexOf("CloseSpellerPopupMsg=done") < 0) {
	        document.all['divPopupMsg'].innerHTML = fMakePopupMsg();
	        document.all['divPopupMsg'].style.visibility = "visible";
	    }
	    else {
	        document.all['divPopupMsg'].style.visibility = "hidden";
	    }
	}

	function fMakePopupMsg() {
	    var strHTML = new String;

	    strHTML += "<table cellpadding='0' cellspacing='0' width='480' bgcolor='#ffffff' style='border:3px #ffffff solid'>";
	    strHTML += "	<tr><td height='252' bgcolor='#ddddff' style='border:1px solid;padding-left:6px;padding-right:3px;font-size:10pt;font-family:�뗭�;font-weight:bold; text-align:left;'>";

	    strHTML += "		<br/>";
	    strHTML += "		<CENTER style='font-size:11pt; font-family:�뗭�; font-weight:bold;'>&lt;�뚮━�� 湲�&gt;</CENTER><br/><br/>";

	    strHTML += "		<div style='line-height:1.5em'>&nbsp;&nbsp;�꾩옱 �대� 援ъ“ 蹂�寃쎌쑝濡� �명븳 �뚯뒪�� �뚮Ц�� �쒖뒪�쒖씠 遺덉븞�뺥븷 �� �덉뒿�덈떎.<br/>";
	    strHTML += "		&nbsp;&nbsp;寃��� 寃곌낵�� 臾몄젣 �덇굅�� 踰꾪듉�� �숈옉�섏� �딆쓣 寃쎌슦 �꾨옒�� �닿껐 諛⑸쾿�� �섑뻾�댁＜�쒓린 諛붾엻�덈떎.<br/><br/>";
	    strHTML += "		&nbsp;&nbsp;1. '�곕━留� 諛곗���'(<a href='http://urimal.cs.pusan.ac.kr/urimal_new/'>http://urimal.cs.pusan.ac.kr/urimal_new</a>)�ъ씠�몄뿉 &nbsp;�묒냽 ��, �붾㈃ �ㅻⅨ履� '�쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린' �대┃<br/><br/>";
	    strHTML += "		&nbsp;&nbsp;2. �명꽣�� 釉뚮씪�곗��� �듭뀡�먯꽌 荑좏궎 ��젣<br/><br/>";
	    strHTML += "		&nbsp;&nbsp;遺덊렪�� �쇱퀜 二꾩넚�⑸땲��. 鍮좊Ⅸ �쒖씪 �댁뿉 �닿껐�섎룄濡� �섍쿋�듬땲��.<br/>";
	    strHTML += "		&nbsp;&nbsp;�ъ슜�섏떆硫댁꽌 遺덊렪�섍굅�� �섏젙�섍퀬 �띠� �댁슜�� �덉쑝�쒕㈃, �ㅼ쓬�� 硫붿씪濡� �섍껄 �④꺼二쇱떆湲� 諛붾엻�덈떎. (urimal@pusan.ac.kr)<br/><br/></div>";
	    
	    
	    //strHTML += "		<div style='line-height:1.5em'>&nbsp;&nbsp;留욎땄踰�/臾몃쾿 寃��ш린�� ���� �ㅽ빐 紐� 媛�吏�瑜� �뚮젮 �쒕┰�덈떎.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;- 300�댁젅諛뽰뿉 寃��� �� �쒕떎.<br/>";
	    //strHTML += "		&nbsp;&nbsp;&nbsp;: <font color='red'>�꾨떃�덈떎.</font> 寃��ы븯�� 湲몄씠�먮뒗 �쒗븳�� �놁뒿�덈떎. ��, �ъ슜�먯쓽 �몄쓽瑜� �꾪빐 300�댁젅 �⑥쐞濡� 援щ퀎�섏뿬 李얠쓣 �� �덉뒿�덈떎. 寃��ъ갹 �꾨옯遺�遺꾩뿉 蹂대㈃ �ㅻⅨ履� �붿궡�쒓� �덉뒿�덈떎. �대┃�섏떆硫� 300�� �⑥쐞濡� 李⑤�濡� �섏뼱媛�硫댁꽌 寃��ы빀�덈떎. (怨� 1|2|3泥섎읆 �댁꽌 �좏깮�섏떎 �� �덇쾶 �섍쿋�듬땲��.)<br/><br/>";
//		strHTML += "		&nbsp;&nbsp;- �섏젙�� �� �쒕떎.<br/>";
//		strHTML += "		&nbsp;&nbsp;&nbsp;: <font color='red'>�꾨떃�덈떎.</font> 寃��� �� �ㅻⅨ履쎌뿉�� �쒖떆�섎뒗 ��移섏뼱 以� �곹빀�� 寃껋쓣 �대┃�섏떆硫�, 洹� �댁젅濡� ��移섑빀�덈떎. �ㅼ떆 �먮옒 �댁젅濡� �뚮━�쒕젮硫� �먮옒 �댁젅�� �대┃�섏떆硫� �⑸땲��. ���몄�留� ��移섏뼱媛� 諛붾Ⅴ吏� �딆쑝硫�, ��移섏뼱 �쒖떆 遺�遺� �ㅻⅨ履쎌뿉 �고븘 紐⑥뼇�� �꾩씠肄섏쓣 �뚮윭 �먰븯�쒕뒗 ��移섏뼱瑜� �낅젰�섏떆硫� 怨좎퀜吏묐땲��.<br/>";
//		strHTML += "		&nbsp;&nbsp;&nbsp;怨좎튇 �댁슜�� 媛��몃떎 �곗떆�ㅻ㈃ '�꾩옱 �섏씠吏� 蹂듭궗'瑜� �섏뿬 遺숈뿬 �곗떆硫� �⑸땲��. �� �뚯젅 媛쒖닔�� �먮Ц �ㅻⅨ履� �꾩뿉�� 李얠쓣 �� �덉뒿�덈떎. �ㅻⅨ 遺덊렪�섏떊 �먯씠 �덉쑝�쒕㈃ �곕씫�� 二쇱떆湲� 諛붾엻�덈떎.<br/><br/>";
//		strHTML += "		&nbsp;&nbsp;留덉�留됱쑝濡� �꾨옒�꾪븳湲��대굹 MS-Word�먯꽌 �� 寃��ш린瑜� �ъ슜�섏떎 遺꾩� (二�)�섎씪�명룷�뚰겕�먯꽌 �ъ꽌 �곗떎 �� �덉뒿�덈떎.<br/>";
//		strHTML += "		&nbsp;&nbsp;�꾨옒�꾪븳湲��⑹� �꾨옒�꾪븳湲��� 留욎땄踰� 寃��ш린瑜� ��移섑븯�� �ъ슜�� �� �덉쑝硫�, MS-Word�⑸룄 媛숈�留�, �ㅻ쪟 醫낅쪟�� �곕씪 �됯퉼�� �щ━�� 蹂댁뿬以띾땲��. <br/>";
//		strHTML += "		&nbsp;&nbsp;踰뺤썝�� 寃곗옱�쒖뒪�쒖뿉�쒕뒗 臾몄꽌 �묒꽦 怨쇱젙�먯꽌 �� 留욎땄踰� 寃��ш린瑜� �ъ슜�섎ŉ, �곕━�섎씪 ��遺�遺� �몃줎�щ룄 �� 留욎땄踰� 寃��ш린瑜� 湲곗궗 �묒꽦�� �쒖슜�⑸땲��. �꾩옄�고렪(e-mail) �쒖뒪�쒖뿉�� �ъ슜�섎뒗 湲곌��� �덉뒿�덈떎.<br/><br/></div>";
	    //strHTML += "		<div style='line-height:1.5em'>&nbsp;&nbsp;2015�� 4�� 27�쇰��� '�쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린'�� 5.6�먯쓣 怨듦컻�⑸땲��.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;5.6�먯� 踰뺣쪧�⑹뼱, �쒖궗�⑹뼱, �몃옒�대� 鍮꾨’�섏뿬 �ㅼ닔 �먮즺媛� 異붽��섏뿀�듬땲��.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;�쒖뒪�� 遺덉븞臾몄젣瑜� �닿껐�섎젮怨� �덈줈�� �쒕쾭瑜� �꾩엯�덉뒿�덈떎. 1李⑤줈 1��瑜� 異붽��덉쑝硫�, 議곕쭔媛� �쒕쾭 1��瑜� 異붽��섍쿋�듬땲��.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;�� �듦퀎�� 諛⑸쾿�� 湲곕컲�� �� 湲곗닠�� 媛쒕컻�섏뿬 �꾩옱 �곸슜 以묒씠硫�, 8�붽꼍�� 6.0�먯쑝濡� 怨듦컻�섍쿋�듬땲��. <br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;�뱀떆 遺덊렪�섍굅�� �섏젙�섍퀬 �띠� �댁슜�� �덉쑝�쒕㈃, �꾨옒�� �곕씫泥섎줈 �섍껄 �④꺼二쇱떆湲� 諛붾엻�덈떎. <br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;'�꾨옒�� �쒓���'�� �④퍡 5.6�먯씠 �섏솕�쇰ŉ �먮룞 �낅뜲�댄듃�� �대쾲 二� 以묒뿉 �쒓났�� �덉젙�낅땲��.<br/><br/>";
	    //strHTML += "		<center>[援щℓ 臾몄쓽 諛� �섍껄 二쇱떎 怨�]</center><br/><center>urimal@pusan.ac.kr, 051) 516 - 9268</center><br/>";
	    //strHTML += "		<div style='line-height:1.5em'>&nbsp;&nbsp;�덈뀞�섏떗�덇퉴? �쒕퉬�� �μ븷 �쒖쓽 ��泥섎쾿�� �뚮젮�쒕━�ㅺ퀬 �⑸땲��.<br/>";
	    //strHTML += "		&nbsp;&nbsp;癒쇱� '�쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린' �ъ슜�� 遺덊렪�� �쇱퀜 二꾩넚�⑸땲��.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;�꾩옱 蹂� �쒕쾭�먯꽌 �뺤긽�곸쑝濡� 寃곌낵瑜� �산린 �섎뱶�쒕떎硫� �꾨옒�� �쒕쾭瑜� �댁슜�댁＜�쒓만 遺��곹븯寃좎뒿�덈떎.<br/>";
	    //strHTML += "		&nbsp;&nbsp;<center><a href='http://164.125.36.75/PnuSpellerISAPI_201406'><font size='5'>異붽� �쒕쾭</font></a></center></br/>";
	    //strHTML += "		&nbsp;&nbsp;�대떦 '異붽� �쒕쾭'�� �쒕쾭 �곹솴�� �곕씪 二쇱냼媛� �섏떆濡� 諛붾�� �� �덉쑝�� 媛��ν븯硫� �꾨옒�� '�곕━留� 諛곗���'瑜� '<font color='red'>利먭꺼李얘린</font>'�대넃�쇱떆怨� '�곕━留� 諛곗���' �곗륫�� '�쒓� 留욎땄踰�/臾몃쾿 寃��ш린' 諛곕꼫瑜� �ъ슜�댁꽌 �묒냽�댁＜�쒕㈃ 媛먯궗�섍쿋�듬땲��.<br/>";
	    //strHTML += "		&nbsp;&nbsp;<center><a href='http://urimal.cs.pusan.ac.kr'><font size='5'>�곕━留� 諛곗���</a></font></center></br/>";
	    //strHTML += "		&nbsp;&nbsp;�ㅼ떆 �� 踰�, 遺덊렪�� �쇱퀜 二꾩넚�⑸땲��.<br/>";
	    //strHTML += "		&nbsp;&nbsp;�꾩옱 �� �명꽣�섏씠�ㅼ뿉 <font color='red'>釉뚮씪�곗� �명솚�� 臾몄젣</font>媛� �덉뒿�덈떎. 寃��� 寃곌낵媛� �쒕�濡� 蹂댁씠吏� �딄굅�� 臾몄젣媛� �덉쑝�쒕㈃ 寃��ъ갹 硫붿씤�� �덈뒗 <font color='red'>'湲곗〈 寃��ш린 媛�湲�'</font> 踰꾪듉�� �대┃�섏뿬 5.0 踰꾩쟾�� �명꽣�섏씠�ㅻ� �ъ슜�댁＜�쒓만 諛붾엻�덈떎.<br/>";
	    //strHTML += "		&nbsp;&nbsp;遺덊렪�� �쇱퀜 二꾩넚�⑸땲��. 鍮좊Ⅸ �쒖씪 �댁뿉 �닿껐�섎룄濡� �섍쿋�듬땲��.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;�ъ슜�섏떆硫댁꽌 遺덊렪�섍굅�� �섏젙�섍퀬 �띠� �댁슜�� �덉쑝�쒕㈃, �ㅼ쓬�� 硫붿씪濡� �섍껄 �④꺼二쇱떆湲� 諛붾엻�덈떎. (urimal@pusan.ac.kr) �덈줈 諛붾�� '�쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린'�먮룄 留롮� 愿��ш낵 醫뗭� �섍껄 遺��곹빀�덈떎.<br/><br/></div>";
	    //strHTML += "		<div style='line-height:1.5em'>&nbsp;&nbsp;8�� 9��(��)~8�� 10��(��), �묒냽 �μ븷�� ���� �뚮┰�덈떎. <br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;2014�� 8�� 9�� �ㅼ쟾 8�쒕��� 8�� 10�� �ㅽ썑 6�쒓퉴吏� '怨좎븬蹂��꾩떎 �섎��꾩꽕鍮� �ㅼ튂�� �곕Ⅸ �댁쟾'�쇰줈, �곕━留� 諛곗��� �쒕쾭媛� �덈뒗 怨�(遺��곕��숆탳)�� �꾧린媛� 怨듦툒�섏� �딆븘 �곕━留� 諛곗��곗뿉 �묒냽�섏떎 �� �놁뒿�덈떎.<br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;1. �쇱떆 : 2014-08-09(��) 08:00 ~ 2014-08-10(��) 18:00 <br/>";
	    //strHTML += "		&nbsp;&nbsp;2. ���� : 遺��곕��숆탳 �먯뿰���곌뎄�ㅽ뿕�� <br/><br/>";
	    //strHTML += "		&nbsp;&nbsp;�뺤쟾 �� �쒕쾭瑜� 諛붾줈 �ш��숉븯�� 理쒕��� �댁슜�� 遺덊렪�⑥씠 �녿룄濡� �섍쿋�듬땲��. <br/><br/></div>";
//	    strHTML += "		<div style='line-height:1.5em'>&nbsp;&nbsp;MS Word�� '�쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린'�� 踰좏��뚯뒪�곕� 紐⑥쭛�⑸땲��.<br/><br/>";
//	    strHTML += "		&nbsp;&nbsp;(二�)�섎씪�명룷�뚰겕�먯꽌�� MS Word�� �쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린瑜� 媛쒕컻�섍퀬 �덉뒿�덈떎.<br/>";
//	    strHTML += "		&nbsp;&nbsp;�꾩옱 踰꾩쟾�쇰줈 �쒗뿕 �ъ슜�섏떎 遺꾨뱾�� �좎껌�� 諛쏄퀬�� �⑸땲��.<br/>";
//	    strHTML += "		&nbsp;&nbsp;�좎껌�� �먰븯�쒕뒗 遺꾨뱾�� �꾨옒 留곹겕瑜� �듯빐 �좎껌�� �댁＜�쒕㈃ �⑸땲��.<br/><br/>";
//	    strHTML += "		&nbsp;&nbsp;�ъ슜�섏떆硫댁꽌 遺덊렪�� �먮뱾�� ���� 硫붿씪濡� 蹂대궡二쇱떆硫� 寃��ш린 媛쒕컻�� �� �꾩��� �섍쿋�듬땲��.<br/><br/>";
//		strHTML += "		&nbsp;&nbsp;�덈줈 諛붾�� '�쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린'�� 留롮� 愿��ш낵 醫뗭� �섍껄 遺��곹빀�덈떎. <br/>";
//		strHTML += "		&nbsp;&nbsp;�꾩옱 �ъ슜�섏떆�� �⑤씪�� 寃��ш린�� �� 踰꾩쟾�낅땲��. <br/>";
//		strHTML += "		&nbsp;&nbsp;�� 寃��ш린瑜� �ъ슜�섏떆硫댁꽌 遺덊렪�섍굅�� �섏젙�섍퀬 �띠� �먯씠 �덉쑝�쒕㈃, 硫붿씪濡� �섍껄 蹂대궡二쇱떆湲� 諛붾엻�덈떎.  <br/><br/>";
//		strHTML += "		&nbsp;&nbsp;�곸뾽�곸씤 紐⑹쟻�쇰줈 �ъ슜�섎젮怨� �섍굅�� 援щℓ瑜� �섏떆怨좎옄 �쒕떎硫� �섎씪�명룷�뚰겕濡� 臾몄쓽�섏떆硫� �⑸땲��. <br/>";
//		strHTML += "		&nbsp;&nbsp;(媛쒖씤�먭쾶 �먮ℓ�섎뒗 �쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린�� '�꾨옒�� �쒓���'�낅땲��.) <br/><br/>";
//		strHTML += "		&nbsp;&nbsp;MS Word 2010 �� �쒓뎅�� 留욎땄踰�/臾몃쾿 寃��ш린�� �대� �뚯뒪�몄슜�� �덉뒿�덈떎. �뚯뒪�명빐 蹂닿퀬 �띠쑝�� 遺꾩� �꾨옒�� 硫붿씪濡� �곕씫 二쇱떆硫� �⑸땲��. <br/><br/></div>";
//	    strHTML += "		<br/><center>[�좎껌�섏떆�� 怨�]</center><br/><center><a href='https://goo.gl/forms/P4R4P7blO10tusap2'>https://goo.gl/forms/P4R4P7blO10tusap2</a></center><br/><br/>";

	    //strHTML += "		<p style='line-height:13pt;'>";

	    //strHTML += "		&nbsp;&nbsp;[異붽��� 湲곕뒫]<br/>";
	    //strHTML += "		&nbsp;&nbsp;蹂듯빀紐낆궗 �꾩뼱�곌린 �쇨��� 泥섎━<br/><br/>";

	    //strHTML += "		&nbsp;&nbsp;[�댁슜]<br/>";
	    //strHTML += "		&nbsp;&nbsp;&nbsp;紐낆궗援�(紐낆궗+紐낆궗)�� �꾩뼱 �⑥빞 �섏�留�, 留욎땄踰� 寃��ш린�먯꽌�� '怨좎쑀紐낆궗쨌�꾨Ц�⑹뼱'�� '�쇰컲 紐낆궗援�'瑜� 援щ퀎�섍린 �대젮�� �꾩뼱 ��怨� 遺숈뿬 ���� 紐⑤몢 �덉슜�⑸땲��. <br/>";
	    //strHTML += "		&nbsp;&nbsp;&nbsp;�먰븳, �쒖�援�뼱���ъ쟾�먯꽌 '^' 湲고샇�� �꾩뼱 ���� �먯튃�닿퀬 遺숈뿬 ���� �덉슜�쒕떎�� �살엯�덈떎. <br/>";
	    //strHTML += "		&nbsp;&nbsp;&nbsp;留욎땄踰� 寃��ш린�� �대윴 �⑥뼱�ㅼ씠 �곗씪 �� �� 臾몄꽌 �댁뿉�쒕뒗 �쇨��깆쓣 �좎��섍퀬�� 泥섏쓬�쇰줈 �ㅼ뼱�� �⑥뼱�� �꾩뼱�곌린�� 留욎떠 援먯젙�⑸땲��. <br/>";
	    //strHTML += "		&nbsp;&nbsp;&nbsp;�덈� �ㅼ뼱 '遺�遺� �쇱튂'濡� 癒쇱� �곗씠硫�, 洹� 臾몄꽌�먯꽌�� '遺�遺� �쇱튂'濡� �꾩뼱 �곕룄濡� �섍퀬, '遺�遺꾩씪移�'濡� 癒쇱� �곗씠硫�, 洹� 臾몄꽌�먯꽌�� '遺�遺꾩씪移�'濡� 遺숈뿬 �곕룄濡� �⑸땲��.<br/>";
	    //strHTML += "		&nbsp;&nbsp;&nbsp;�대떦 �ㅻ쪟�� �뚯깋�쇰줈 �쒖떆�⑸땲��.<br/><br/>";

	    //strHTML += "		</p>";

	    //strHTML += "		<center>urimal@pusan.ac.kr <a href='#' onclick='fClosePopupMsg();fShowUserReportPopup(\"./\");'>[�섍껄 蹂대궡湲�]</a></center><br/><br/>";

//		strHTML += "		&nbsp;&nbsp;<font style='font-weight:normal;'>1�꾩뿬 �숈븞 留롮� 怨좊� �앹뿉 �섏삩 �쒖뒪�쒖엯�덈떎. 留롮� 愿��ш낵 醫뗭� �섍껄 遺��곹빀�덈떎.</font><br/><br/>";

	    strHTML += "	</td></tr>";
	    strHTML += "	<tr><td align='right' valign='middle' bgcolor='#000000' height='20'>";
	    strHTML += "		<form name='formPopup'>";
	    strHTML += "		<input type='checkbox' name='chkTimer'>";
	    strHTML += "		<font face='�뗭�' style='font-size:8pt;color:#ffffff;'>�ㅻ뒛 �섎（ �� 李쎌쓣 �댁� �딆쓬</font>";
	    strHTML += "		<a href='javascript:fClosePopupMsg();' style='font-size:8pt;color:#ffffff;font-weight:bold;'>[�リ린]</a>";
	    strHTML += "		</form>";
	    strHTML += "	</td></tr>";
	    strHTML += "</table>";
	    return strHTML;
	}*/