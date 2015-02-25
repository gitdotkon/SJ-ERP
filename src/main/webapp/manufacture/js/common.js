


onCheckEnter = function(e){
	var keyCode = null;

        if(e.which)
            keyCode = e.which;
        else if(e.keyCode) 
            keyCode = e.keyCode;
            
        if(keyCode == 13) 
			onStockSearch();


};


function createNewFieldToForm(FormId, FieldId)
{
	if (document.getElementById(FormId)[FieldId] == null)
	{
		var newItem = document.createElement("input");
		newItem.id = FieldId;
		newItem.name = FieldId;
		newItem.type = "hidden";
		newItem.value = " ";
		document.getElementById(FormId).appendChild(newItem);
	}
};


