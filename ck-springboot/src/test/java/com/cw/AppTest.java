package com.cw;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cw.entity.Record;
import com.cw.mapper.RecordMapper;
import com.cw.service.RecordService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Pattern;
@SpringBootTest
public class AppTest {
    @Resource
    private RecordService recordService;
    @Resource
    RecordMapper recordMapper;
    @Test
    public void test(){
        String regx="\\S{2,10}";
        String context="gefewd";
        boolean matches = Pattern.matches(regx, context);
        System.out.printf("result,"+matches);
    }
    @Test
    public void mapper(){
        //Record record = recordMapper.queryOne(1);
        //System.out.printf(record.toString());

        //IPage<Record> page = recordService.listServer(1, 5, "辣", 5, 6);
        //System.out.printf("sd");

        IPage<Record> page = recordService.listServer2(1, 5, "辣", 5, 6);
        List<Record> records = page.getRecords();
        System.out.println(records.toString());
        System.out.println(String.valueOf(page.getTotal()));

    }
}
