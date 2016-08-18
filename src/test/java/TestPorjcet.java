import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestPorjcet {
	
	private ApplicationContext ac;
	
    @Before
    public void Before() {
        ac = new ClassPathXmlApplicationContext(new String[] { "spring.xml",
                "spring-mybatis.xml" });

    }


    
    @Test
    public void test1(){
    	
    	
    }
    
}
