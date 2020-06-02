public class DogLauncher {
	public static void main(String[] args) {
		Dog d = new Dog(5);
		Dog d3 = new Dog(8);
		Dog d2 = new Dog(100);

		//Dog bigger = d.maxDog(d2);
		Dog bigger = Dog.maxDog(d, d3);
		bigger.makeNoise();

		//System.out.println(d.binomen);
		//System.out.println(d2.binomen);
		//System.out.println(Dog.binomen);
//		d.makeNoise();
	}
} 