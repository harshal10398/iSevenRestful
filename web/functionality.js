/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function JsonToTable(resultJson,args){
    console.log(resultJson);
    var resultObj=JSON.parse(resultJson);
    var string="";
    for(var i=0;i<resultObj.length;i++){
        string+="<tr>";
        for(var j=0;j<args.fields.length;j++)
            string+="<td>"+(resultObj[i].values[args.fields[j]])+"</td>";
        string+="</tr>";
    }
    string+="";
    args.tableId.innerHTML=string;
}
function get(url,callback,args){
    var xhttp=new XMLHttpRequest();
    xhttp.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200)
            if(callback!==null)
                callback(this.responseText,args);
    };
    
    xhttp.open("GET",url,async=true,cache=false);
    xhttp.send();
}

function getPartyAgent(input,container,fields){
    
    var args={"tableId":container,"fields":fields};
    if(input.value===null||input.value==="")
        get("PartyAgent/all",JsonToTable,args);
    else
        get("PartyAgent/"+input.value,JsonToTable,args);
}
function jsonToTable(resultJson,args){
    console.log(resultJson);
    var resultObj=JSON.parse(resultJson);
    console.log(resultObj);
    var string="";
    string+="<tr>"
    for(var i=0;i<args.fields.length;i++)
        string+="<th>"+args.fields[i]+"</th>";
    string+="</tr>";
    for(var i=0;i<resultObj.length;i++){
        string+="<tr>";
        for(var j=0;j<args.fields.length;j++)
            string+="<td>"+(resultObj[i].values[args.fields[j]])+"</td>";
        string+="</tr>";
    }
    string+="";
    args.output.innerHTML=string;
}
function universalGet(input,output,fields,renderFunction,url,nullText){
    input=document.getElementById(input);
    var args={"output":document.getElementById(output),"fields":fields};
    console.log(input.value);
    if(input.value===null||input.value===""||input.value===''||input.value===undefined)
        get(url+"/"+nullText,renderFunction,args);
    else
        get(url+"/"+input.value,renderFunction,args);
}
function formDataToJson(formDataString){
    var pairs=formDataString.split('&');
    var result={};
    pairs.forEach(function(pairs){
        pairs=pairs.split('=');
        result[pairs[0]]=decodeURIComponent(pairs[1]||'');
    });
    return JSON.parse(JSON.stringify(result));
}
function errorNotifier(response){
    console.log(response);
    if(response.includes("Duplicate entry"))
        window.alert("Duplicate GSTIN!");
}
function universalPost(formId,url,callback){
    var form=document.getElementById(formId);
    var formData=new FormData(form);
    var xhttp=new XMLHttpRequest(callback);
    form.reportValidity();
    xhttp.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200)
            callback(this.responseText);
    }
    xhttp.open("POST",url,async=true,cache=false);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(new URLSearchParams(new FormData(document.getElementById(formId))).toString());
}

function universalPut(formId,url,callback){
    var form=document.getElementById(formId);
    var formData=new FormData(form);
    var xhttp=new XMLHttpRequest(callback);
    form.reportValidity();
    xhttp.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200)
            callback(this.responseText);
    }
    xhttp.open("PUT",url,async=true,cache=false);
//    for(element in form.elements){
//    console.log(document.getElementsById("partyRadio").value);
    for(var i=0;i<form.elements.length;i++){
        if(form.elements[i].name=="")
            form.elements[i].name="foo";
        console.log(form.elements[i].name+" -- "+form.elements[i].value);
        xhttp.setRequestHeader(form.elements[i].name,form.elements[i].value);
    }
//    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    //testing netbeans git!!!!
    xhttp.send(new URLSearchParams(new FormData(document.getElementById(formId))).toString());
}