

var callCount = 0;
var circles = [];
var gravElement = document.getElementById('gravContainer'); 
gravElement.style.backgroundColor = '#000000';
gravElement.style.position = 'relative';
gravElement.style.overflow = 'hidden';
gravElement.style.padding = 0;
gravElement.style.margin = 0;
var windowWidth = gravElement.offsetWidth;

var windowHeight = 0;
var par;


function getviewheight(){
	

	var par = gravElement.parentNode;
	var parentHeightfound = false;
	while(!parentHeightfound){
	
	
		if(par.tagName === 'BODY'){
			
			parentHeightfound = true;
		    return window.innerHeight;
					
		}else{
			
			if(par.offsetHeight !== undefined && par.offsetHeight>0){
				
				parentHeightfound = true;
				if(par.offsetHeight > window.innerHeight){
					return  window.innerHeight;
				}else{
			        return par.offsetHeight;
				}
			}
		}
			par = par.parentNode;
		
	}


}

windowHeight = getviewheight();
gravElement.style.height = windowHeight + 'px';

var assets;
var gravElement = document.getElementById('gravContainer');
//var GravC = 0.00000000006674;
var GravC = 0.00005*(windowWidth/1920);
var duration = 1000; // ms
var currentTime = Date.now();
var startTime = currentTime;
var runtime = 0;
var now;
var zoomin = false;
var mouseGrav = new MouseGrav({offsetX: windowWidth/2, offsetY: 5000});

function MouseGrav(e){

	this.posX = e.offsetX;
	this.posY = e.offsetY;
	this.moveable = true;
	this.MASS = 0.000001;
	this.mass = function(){
		
		return this.MASS;
	}
		
}

function Circle(e){

	this.posX = e.offsetX;
	this.posY = e.offsetY;
	this.velX = 0;//(Math.floor(Math.random()* 201)-100)/1000;
	this.velY = 0;//(Math.floor(Math.random()* 201)-100)/1000;
	this.forceX = 0;
	this.forceY = 0;
	this.radius = Math.floor(Math.random()*Math.min(windowWidth,windowHeight)*0.005)+Math.min(windowWidth,windowHeight)*0.002;
	this.diameter = 2*this.radius;
	
	this.mass = function(){
		return this.radius*this.radius*Math.PI*(1920/Math.min(windowWidth,windowHeight));
	} 
	
	this.color = "rgb("+(Math.floor(Math.random() * 255))+","+(Math.floor(Math.random() * 255))+","+(Math.floor(Math.random() * 255))+")";
	this.absorb = function(r){
		var oldRadius
		this.radius++;
	};
	
	
	
	this.stayOnScreen = function(){
		if(this.posX+this.radius > windowWidth){
		this.posX = windowWidth-this.radius;
		this.velX = -this.velX;
		this.forceX =  -this.forceX;
		}
		
		if(this.posX-this.radius < 0){
		this.posX = this.radius;
		this.velX  = -this.velX;
		this.forceX = -this.forceX;
		}
		
		if(this.posY+this.radius > windowHeight){
		this.posY = windowHeight-this.radius;
		this.velY  = -this.velY;
		this.forceY = -this.forceY;
		}
		if(this.posY-this.radius < 0){
		this.posY = this.radius;
		
		this.velY  = -this.velY;
		this.forceY = -this.forceY;
		
		}
		
	}
	
}


function viewGrav(){
	

var svg = '';

svg += '<button id="g0" style="position:absolute; left:0; top:0; width:'+windowWidth/4+'px; height:30px; background-color:#777777; border:none; opacity:0.8; font-size:80%;">Gravity-0</button>';
	
svg += '<button id="g10" style="position:absolute; left:'+windowWidth/4+'px; top:0; width:'+windowWidth/4+'px; height:30px; background-color:#AA7777; border:none; opacity:0.8; font-size:80%;">Gravity-10</button>';

svg += '<button id="g50" style="position:absolute; left:'+windowWidth/2+'px; top:0; width:'+windowWidth/4+'px; height:30px; background-color:#77AA77; border:none; opacity:0.8; font-size:80%;">Gravity-50</button>';

svg += '<button id="g100" style="position:absolute; left:'+3*windowWidth/4+'px; top:0; width:'+windowWidth/4+'px; height:30px; background-color:#7777AA; border:none; opacity:0.8; font-size:70%;">Gravity-100</button>';

svg += '<button id="de" style="position:absolute; left:0px;  top:'+(windowHeight-30)+'px; width:'+windowWidth/4+'px; height:30px; background-color:#770077; border:none; opacity:0.8; font-size:75%;">Dark Energy</button>';

svg += '<button id="si" style="position:absolute; left:'+3*windowWidth/4+'px; top:'+(windowHeight-30)+'px; width:'+windowWidth/4+'px; height:30px; background-color:#AA77AA; border:none; opacity:0.8; font-size:80%;">Singularity</button>';

svg += '<button id="zeromass" style="position:absolute; left:'+windowWidth/4+'px; top:'+(windowHeight-30)+'px; width:'+windowWidth/2+'px; height:30px; background-color:#0077AA; border:none; opacity:0.8; font-size:80%;">Zero Mass</button>';


svg += '<svg id="gravRender" xmlns="http://www.w3.org/2000/svg" version="1.1" width="'+windowWidth+'" height="'+windowHeight+'" viewBox="0,0,'+windowWidth+','+windowHeight+'">';



for(cirs in circles){
	
	if(circles[cirs].radius>0.03*windowWidth && circles[cirs].radius<0.28*windowWidth){
		svg += ' <defs>';
		
   svg += ' <radialGradient id="f'+cirs+'" cx="50%" cy="50%" r="'+(circles[cirs].radius/(0.28*windowWidth)*100)+'%" fx="50%" fy="50%">';
     svg += ' <stop offset="30%" style="stop-color:'+circles[cirs].color+';stop-opacity:0" />';
      svg += '<stop offset="100%" style="stop-color:'+circles[cirs].color+';stop-opacity:1" />';
    svg += '</radialGradient>';
 svg += ' </defs>';

	svg +=  '<circle cx="'+(circles[cirs].posX)+'" cy="'+(circles[cirs].posY)+'" r="'+circles[cirs].radius+'"   fill="url(#f'+cirs+')"/>';
	}else if(circles[cirs].radius>=0.28*windowWidth){
		
			svg += ' <defs>';
   svg += ' <radialGradient id="f'+cirs+'" cx="50%" cy="50%" r="90%" fx="50%" fy="50%">';
     svg += ' <stop offset="0%" style="stop-color:rgb(255,255,255); stop-opacity:1" />';
      svg += '<stop offset="20%" style="stop-color:'+circles[cirs].color+';stop-opacity:0" />';
    svg += '</radialGradient>';
 svg += ' </defs>';

	svg +=  '<circle cx="'+(circles[cirs].posX)+'" cy="'+(circles[cirs].posY)+'" r="'+circles[cirs].radius+'"   fill="url(#f'+cirs+')"/>';
		
	}else{
		
		svg +=  '<circle cx="'+(circles[cirs].posX)+'" cy="'+(circles[cirs].posY)+'" r="'+circles[cirs].radius+'" fill="'+circles[cirs].color+'" />';
	}

}

 
 svg +=  '<circle cx="'+(mouseGrav.posX)+'" cy="'+(mouseGrav.posY)+'" r="'+windowWidth*0.01+'" stroke-width="4" stroke="rgb(255,255,255)" />';
	   svg += ' Sorry, your browser does not support inline SVG.';

 svg += '</svg>';
 
 
return svg;

}

if ( !window.requestAnimationFrame ) {

	window.requestAnimationFrame = ( function() {

		return window.webkitRequestAnimationFrame ||
		window.mozRequestAnimationFrame ||
		window.oRequestAnimationFrame ||
		window.msRequestAnimationFrame ||
		function( /* function FrameRequestCallback */ callback, /* DOMElement Element */ element ) {

			window.setTimeout( callback, 1000 / 60 );

		};

	} )();

}


function windowChanges(){
	if(gravElement.offsetWidth !== windowWidth || getviewheight() !== windowHeight){
		assets = viewGrav();
		
		windowWidth = gravElement.offsetWidth;
		
		if (!/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i.test(navigator.userAgent)) {
            windowHeight = getviewheight();
			gravElement.style.height = windowHeight + 'px';
      }
        
	}	
	


}

function  spawnCircle(){
	circles.unshift(new Circle({offsetX:Math.random()*windowWidth,offsetY:Math.random()*windowHeight}));
}

function runballs(qty){

	if(circles.length < qty){
		spawnCircle();
		return runballs(qty);
		
	}else{
		
		return;
		}

}


function clear(){
	
	circles = [];
	
}




function play(){
	
now = Date.now();
var deltat = now - currentTime;
runtime = now - startTime;
currentTime = now;

   for(cirs in circles){
	  
	   
	 circles[cirs].posX = circles[cirs].posX + deltat*circles[cirs].velX;
	circles[cirs].posY = circles[cirs].posY + deltat*circles[cirs].velY;
	
	var relaxation = 1.0;
	if(Math.sqrt(Math.pow(circles[cirs].velX,2)+Math.pow(circles[cirs].velX,2))*circles[cirs].mass() > 20){
		relaxation = 0.95;
	}
	 circles[cirs].velX = relaxation* circles[cirs].velX +circles[cirs].forceX/circles[cirs].mass()*deltat
	 circles[cirs].velY = relaxation* circles[cirs].velY +circles[cirs].forceY/circles[cirs].mass()*deltat
	 
   }

var absorbtionlist = [];
var destroylist = [];
  for(me in circles){
	  circles[me].forceX = 0;
		circles[me].forceY = 0;
		for(them in circles){
		
         if(me !== them){			
			var distX = circles[them].posX - circles[me].posX;
			var distY = circles[them].posY - circles[me].posY;
			var distbetween = Math.sqrt(Math.pow(distX,2) + Math.pow(distY,2));
				if(distbetween < (circles[them].radius+circles[me].radius)){
					distbetween = circles[them].radius+circles[me].radius;
					if(circles[them].radius<circles[me].radius){
						 var isInabslist = false;
							for(abs in absorbtionlist){
								 
							if(absorbtionlist[abs].absorbs === me && absorbtionlist[abs].absorbs === them){
								isInabslist = true;
								break;
							}
							 }
							if(!isInabslist){
							 absorbtionlist.push({'who':me,'absorbs':them});
							}
						   
				        }
				}						
 
			var force = GravC*circles[me].mass()*circles[them].mass()/(distbetween*distbetween);
			
			
			
		circles[me].forceX = circles[me].forceX + force*(distX/distbetween);
		circles[me].forceY = circles[me].forceY + force*(distY/distbetween);
		 }
		 
		}
		
		var distXearth = mouseGrav.posX - circles[me].posX;
			var distYearth = mouseGrav.posY - circles[me].posY;
			var distEarth= Math.sqrt(Math.pow(distXearth,2) + Math.pow(distYearth,2));
				if(distEarth < (circles[me].radius)){
					distEarth = circles[me].radius;
				}	
     var forceEarth = GravC*circles[me].mass()*mouseGrav.mass()/(distEarth*distEarth);
	  circles[me].forceX = circles[me].forceX + forceEarth *(distXearth/distEarth);	
	 circles[me].forceY = circles[me].forceY + forceEarth *(distYearth/distEarth);
	 circles[me].stayOnScreen();
	
     if(circles[me].radius > windowWidth*0.3){
		 destroylist.push(me);
	 }
   
	
   }
 
 //absorbtionlist = [];
   for(abs in absorbtionlist){
	   var prevMass = circles[Number(absorbtionlist[abs].who)].mass();
	   var velInitialX = circles[Number(absorbtionlist[abs].who)].velX;
	   var velInitialY = circles[Number(absorbtionlist[abs].who)].velY;
	   
	 circles[Number(absorbtionlist[abs].who)].radius = Math.sqrt(Math.pow(circles[absorbtionlist[abs].who].radius,2) + Math.pow(circles[absorbtionlist[abs].absorbs].radius,2));
	 
	 circles[Number(absorbtionlist[abs].who)].velX = (circles[Number(absorbtionlist[abs].who)].velX * prevMass  + circles[absorbtionlist[abs].absorbs].velX*circles[absorbtionlist[abs].absorbs].mass())/(prevMass + circles[absorbtionlist[abs].absorbs].mass());
	 circles[Number(absorbtionlist[abs].who)].velY = (circles[Number(absorbtionlist[abs].who)].velY * prevMass  + circles[absorbtionlist[abs].absorbs].velY*circles[absorbtionlist[abs].absorbs].mass())/(prevMass + circles[absorbtionlist[abs].absorbs].mass());
	 
	circles[Number(absorbtionlist[abs].who)].forceX = circles[Number(absorbtionlist[abs].who)].forceX   + 0.1*(circles[absorbtionlist[abs].who].mass()*(circles[absorbtionlist[abs].who].velX-velInitialX))/deltat;
	circles[Number(absorbtionlist[abs].who)].forceY = circles[Number(absorbtionlist[abs].who)].forceY   + 0.1*(circles[absorbtionlist[abs].who].mass()*(circles[absorbtionlist[abs].who].velY-velInitialY))/deltat;

	  var endcircles = circles.slice(Number(absorbtionlist[abs].absorbs)+1);
	  var startcircles =  circles.slice(0,Number(absorbtionlist[abs].absorbs));
	circles = [];
	 circles = startcircles.concat(endcircles);
    spawnCircle();

	
   }
   

    for(me in destroylist){
	  var endcircles = circles.slice(Number(destroylist[me])+1);
	  var startcircles =  circles.slice(0,Number(destroylist[me]));
	 circles = [];
	 circles = startcircles.concat(endcircles);
	 //spawnCircle();
	}

   assets = viewGrav();
}

function handleInterface(e){


	if(e.type==='mousedown'){
	

		circles.push(new Circle(e));
		
		if(e.target == document.getElementById('g0')){
			clear();
			runballs(0);
		}else if(e.target == document.getElementById('g10')){
			
			clear();
			runballs(10);
		}else if(e.target == document.getElementById('g50')){
			clear();
			runballs(50);
			
		}else if(e.target == document.getElementById('g100')){
			
			clear();
			runballs(100);
		}else if(e.target == document.getElementById('de')){
			mouseGrav.MASS = -50000*(1920/windowWidth);;
			
		}else if(e.target == document.getElementById('si')){
			mouseGrav.MASS = 50000*(1920/windowWidth);
		}else if(e.target == document.getElementById('zeromass')){
			mouseGrav.MASS = 0;	
			
		}else{
				mouseGrav.moveable = true;
		}
		
		
			assets = viewGrav();
			
	}
	
	if(e.type==='mouseup'){
	}

	if(e.type==='mousemove' && e.target.id == 'gravRender'){
		if(mouseGrav.moveable){
		mouseGrav.posX = e.offsetX;
		mouseGrav.posY = e.offsetY;
		}
	}
	
	
	if(e.type==='dblclick'){
		
		mouseGrav.moveable = false;
		mouseGrav.posX = e.offsetX;
		mouseGrav.posY = e.offsetY;
	}
	
}


gravElement.addEventListener('touchstart', function(e){
        var touchobj = e.changedTouches[0]; // reference first touch point (ie: first finger)
		     
		circles.push(new Circle({offsetX: touchobj.pageX-gravElement.offsetLeft, offsetY: touchobj.pageY-gravElement.offsetTop}));
		
		if(touchobj.target == document.getElementById('g0')){
			clear();
			runballs(0);
		}else if(touchobj.target == document.getElementById('g10')){
			
			clear();
			runballs(10);
		}else if(touchobj.target == document.getElementById('g50')){
			clear();
			runballs(50);
			
		}else if(touchobj.target == document.getElementById('g100')){
			
			clear();
			runballs(100);
			
		}else if(touchobj.target == document.getElementById('de')){
			mouseGrav.MASS = -50000*(1920/windowWidth);
			
		}else if(touchobj.target == document.getElementById('si')){
			mouseGrav.MASS = 50000*(1920/windowWidth);
		}else if(touchobj.target == document.getElementById('zeromass')){
			mouseGrav.MASS = 0;	
			
		}else{
		mouseGrav.posX = touchobj.pageX-gravElement.offsetLeft;
		mouseGrav.posY = touchobj.pageY-gravElement.offsetTop;
		}
		
	    assets = viewGrav();	  
		
        e.preventDefault();
});
 
gravElement.addEventListener('touchmove', function(e){
        var touchobj = e.changedTouches[0]; // reference first touch point for this event

        mouseGrav.posX = touchobj.clientX-gravElement.offsetLeft;
	mouseGrav.posY = touchobj.clientY-gravElement.offsetTop;
	assets = viewGrav();
        e.preventDefault();
});
 
gravElement.addEventListener('touchend', function(e){
         var touchobj = e.changedTouches[0]; // reference first touch point for this event
		 
	    mouseGrav.posX = touchobj.pageX-gravElement.offsetLeft;
	    mouseGrav.posY = touchobj.pageY-gravElement.offsetTop;
	    assets = viewGrav();
        e.preventDefault();
});


gravElement.ondblclick = function(event){
	handleInterface(event);
}

gravElement.onorientationchange = function(e) {
e.preventDefault();
};

gravElement.onmousemove = function(event){
	
	handleInterface(event);
	 event.preventDefault();
	
}

gravElement.onmousedown = function(event){

		handleInterface(event);
		event.preventDefault();
}

gravElement.onmouseup = function(event){
	handleInterface(event);
	event.preventDefault();
}


function render(){
play();
gravElement.innerHTML = assets;
}



function run(){
requestAnimationFrame(function() { run(); });
windowChanges();
render();
}



function playgame(){
var self = this;
runballs(100);
assets = viewGrav();
run();
}

playgame();
