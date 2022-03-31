package testing1;

public class Shape {	
	
	
	public double getArea() {
		return 0.0;
	}
		
	public static double sumAreas(Shape[] list) {
		double sum = 0;
		for(int i =0; i < list.length;i++) {
			sum = sum + list[i].getArea();
		}
		return sum;
	}

	public static void main(String[] args) {
		Circle circle = new Circle(2.0);
		
		Circle c1 = new Circle(1.0);
		Circle c2 = new Circle(2.0);
		Circle c3 = new Circle(3.0);
		Shape[] list = new Shape[] {c1, c2, c3};
		
		if (circle.getArea() == 12.566370614359172 &&
			sumAreas(list) == 43.982297150257104) {
			System.out.println("passed.");
		} else {
			System.out.println("failed.");
		}

	}
}

class Circle extends Shape {
	private double radius = 0;
	Circle(double x) {
		this.radius = x;
	}
	
	public double getArea() {
		return this.radius*this.radius* Math.PI;
	}
	
}


