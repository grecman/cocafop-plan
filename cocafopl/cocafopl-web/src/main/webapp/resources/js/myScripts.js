function check(f, v) {
	var fr = document.forms[f];
	var errs = "";
	for (n in v) {
		if (! fr[n].value.match(v[n])) {
			//alert(fr[n].name + "   #"+ fr[n].value + "* "+ v[n]);
			errs = errs + fr[n].name + ": " + fr[n].value + "\n";
		}
	}
	if (errs) {
		alert("Chyba syntaxe:\n" + errs);
		return false;
	}
	fr.submit();
	return true;
}

function modalConfirmationDeleteAll() {
	$("#dialog-confirm").dialog({
		resizable : false,
		autoOpen : false,
		height : 180,
		modal : true,
		buttons : {
			"Smazat vše" : function() {
				// todo
				$(this).dialog("close");
			},
			"Zpět" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#opener").click(function() {
		$("#dialog-confirm").dialog("open");
	});
}
