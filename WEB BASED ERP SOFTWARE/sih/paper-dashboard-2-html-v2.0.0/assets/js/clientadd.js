
var Button = document.getElementById("ClientButton")

function addButton(){

	window.alert(" hello");
	var name = document.getElementById("ClientName").value;

	var firebaseRef = firebase.database().ref();
	firebaseRef.child("client").set(name);
}