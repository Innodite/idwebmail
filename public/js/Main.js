/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function isExist(id){
    var obj = document.getElementById("cke_"+id);
    if (obj)
        return true;
    return false;
}

function iniciaCKE(id){
   if (!isExist(id))
       CKEDITOR.replace(id);
}

function isVisibleCKE(id){
    if(isExist(id)){
        if (document.getElementById('cke_'+id).style.visibility == 'visible' ||
            document.getElementById('cke_'+id).style.display != 'none')
            return true;
    }
    return false;
}

function hideCKE(id){
    if(isExist(id))
        if(isVisibleCKE(id)){
            document.getElementById(id).style.display = 'none';
            document.getElementById('cke_'+id).style.display = 'none';
        }
}

function showCKE(id){
    if(isExist(id))
        if(!isVisibleCKE(id)){
            document.getElementById(id).style.display = 'none';
            document.getElementById('cke_'+id).style.display = 'block';
        }
}

function setCkeValue(id,value){
    if(isExist(id))    
        CKEDITOR.instances[id].setData(value);
}

function getCkeValue(id){
    if(isExist(id))    
        return CKEDITOR.instances[id].getData();
    return null;
}

function getCkePlainText(id){
    if(isExist(id))
        return CKEDITOR.instances[id].document.getBody().getText();
    return null;
}