





function addEmploy(){

	var name = document.getElementById("EmployName").value;
	
	var firebaseRef = firebase.database().ref();
	firebaseRef.child("employ").set(name);

}