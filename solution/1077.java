import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    while(sc.hasNext()) {
        int n=sc.nextInt(); 
        boolean flag=false;
        boolean flag1=false;
	    for(int i=10000;i<=99999;i++) {
		    flag=false;
		    if(i%n==0) {
		        String str1=i+"";
		        if(Integer.parseInt(str1.substring(0, 3))%n==0&&Integer.parseInt(str1.substring(1, 4))%n==0&&Integer.parseInt(str1.substring(2, 5))%n==0) {
			    flag=true;
			    flag1=true;
		    }
		}
		if(flag) {
			System.out.println(i);
		}
	}
	if(!flag1) {
		System.out.println("No");
	}
	System.out.println();
	}
}	
}

