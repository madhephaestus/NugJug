//Your code here

double baseThickness = 5
double widthOfScoop = 200
double opening = 25
//create an extruded polygon
CSG polygon = Extrude.points(new Vector3d(0, 0, widthOfScoop),// This is the  extrusion depth
                new Vector3d(0,0),// All values after this are the points in the polygon
                new Vector3d(widthOfScoop,0),// Bottom right corner
                new Vector3d(widthOfScoop,baseThickness),// upper right corner
                new Vector3d(20,baseThickness)// upper left corner
        ).rotx(-90)
        .toYMin()

CSG hole =new Cylinder(opening-baseThickness,opening-baseThickness,40,(int)12).toCSG() // a one line Cylinder
			
CSG simpleSyntax =new Cylinder(opening,opening,40,(int)12).toCSG() // a one line Cylinder
				.difference(hole)
				.rotz(15)
				.roty(-90)
				.movey(widthOfScoop/2)
				.movex(widthOfScoop)
				.toZMin()
CSG coneHole =new Cylinder(widthOfScoop/2-baseThickness,opening-baseThickness,widthOfScoop,(int)12).toCSG() // a one line Cylinder
			
CSG cone =new Cylinder(widthOfScoop/2,opening,widthOfScoop,(int)12).toCSG() // a one line Cylinder
			.difference(coneHole)
			.rotz(15)
			.movey(widthOfScoop/2)
			.roty(-90)
			.movez(opening)		
CSG scoopSecion = polygon.intersect(cone.hull())

CSG box =cone.getBoundingBox()	
		.toZMin()
cone=cone.intersect(box)		

CSG handlePart = new Cube(	widthOfScoop/2+opening,
			15,
			15
			).toCSG()
			.toZMin()
			.toXMin()

CSG handle =new Cylinder(15,15,100,(int)12).toCSG() // a one line Cylinder
			.union([handlePart,handlePart.movez(85)])
			.roty(-90)
			.movez(7.5)
			.movey(widthOfScoop/2)
			.movez(widthOfScoop/2+opening)
			.movex(widthOfScoop/3)
			.difference(cone.hull())
import eu.mihosoft.vrl.v3d.*;
import javafx.scene.text.Font;

Font font = new Font("Harrington",  30);

CSG text =CSG.unionAll( TextExtrude.text((double)5.0," NugJug",font))
	.rotx(90)
	.toZMin()
	.movey(1)
	.rotz(-20)
	.movez(10)
	
 
return CSG.unionAll([scoopSecion,simpleSyntax,cone,handle,text])