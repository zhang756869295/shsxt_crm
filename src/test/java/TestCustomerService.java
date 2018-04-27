import com.shsxt.crm.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by lp on 2018/1/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:servlet-context.xml"})
public class TestCustomerService {
    @Resource
    private CustomerService customerService;

    @Test
    public  void test01(){
        customerService.updateCustomerLossState();
    }
}
