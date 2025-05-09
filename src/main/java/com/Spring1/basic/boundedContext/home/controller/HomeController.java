package com.Spring1.basic.boundedContext.home.controller;

import com.Spring1.basic.boundedContext.member.entity.Member;
import com.Spring1.basic.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class HomeController {

    private int count;

    private List<Person> people;

    @Autowired
    private final MemberService memberService;

    public HomeController(MemberService memberService){
        count=-1;
        people=new ArrayList<>();

        this.memberService=memberService;
    }


    @GetMapping("/home/main")
    @ResponseBody
    public String showHome(){
        return "안녕하세요";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showHome2(){
        return "환영합니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showHome3(){
        return "스프링부트는 획기적이다.";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease(){
        count++;
        return count;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b){
        return a+b;
    }

    @GetMapping("/home/returnBoolean")
    @ResponseBody
    public boolean showBoolean(){
        return true;
    }

    @GetMapping("/home/returnDouble")
    @ResponseBody
    public double showDouble(){
        return Math.PI;
    }

    @GetMapping("/home/returnIntList")
    @ResponseBody
    public List<Integer> showIntArray(){
        List<Integer> list=new ArrayList<>(){{
            add(10);
            add(20);
            add(30);
        }};

        return list;
    }

    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showMap(){
        Map<String, Object> map= new LinkedHashMap<>(){{
          put("id",1);
          put("speed",100);
          put("name","아반떼");
          put("relatedIds",new ArrayList<>(){{
              add(1);
              add(2);
              add(3);
          }});
        }};

        return map;
    }

    class Car{
        private final int id;
        private final int speed;
        private final String name;
        private final List<Integer> relatedIds;

        public Car(int id, int speed, String name, List<Integer> relatedIds) {
            this.id = id;
            this.speed = speed;
            this.name = name;
            this.relatedIds = relatedIds;
        }

        public List<Integer> getRelatedIds() {
            return relatedIds;
        }

        public String getName() {
            return name;
        }

        public int getSpeed() {
            return speed;
        }

        public int getId() {
            return id;
        }
    }

    @GetMapping("/home/returnCar")
    @ResponseBody
    public Car showReturnCar(){
        Car car = new Car(1,100,"아반떼",new ArrayList<>(){{
            add(10);
            add(20);
            add(30);
        }});

        return car;
    }

    @AllArgsConstructor
    @Getter
    class Car2{
        private final int id;
        private final int speed;
        @Setter
        private String name;
        private final List<Integer> relatedIds;
    }

    @GetMapping("/home/returnCar2")
    @ResponseBody
    public Car2 showReturnCar2(){
        Car2 car = new Car2(1,100,"아반떼",new ArrayList<>(){{
            add(10);
            add(20);
            add(30);
        }});

        car.setName(car.getName()+"v2");

        return car;
    }

    @GetMapping("/home/returnCarMapList")
    @ResponseBody
    public List<Map<String, Object>> showReturnCarMapList(){
        Map<String, Object> carMap1= new LinkedHashMap<>(){{
            put("id",1);
            put("speed",100);
            put("name","아반떼");
            put("relatedIds",new ArrayList<>(){{
                add(2);
                add(3);
                add(4);
            }});
        }};

        Map<String, Object> carMap2= new LinkedHashMap<>(){{
            put("id",2);
            put("speed",200);
            put("name","산타페");
            put("relatedIds",new ArrayList<>(){{
                add(5);
                add(6);
                add(7);
            }});
        }};

        List<Map<String, Object>>list=new ArrayList<>(){{
            add(carMap1);
            add(carMap2);
        }};

        return list;
    }


    @GetMapping("/home/returnCarList")
    @ResponseBody
    public List<Car2> showReturnCarList(){
        Car2 car1 = new Car2(1,100,"아반떼",new ArrayList<>(){{
            add(20);
            add(30);
            add(40);
        }});

        Car2 car2 = new Car2(2,200,"산타페",new ArrayList<>(){{
            add(50);
            add(60);
            add(70);
        }});

        List<Car2> list=new ArrayList<>(){{
           add(car1);
           add(car2);
        }};

        return list;
    }

    @AllArgsConstructor
    @Getter
    class Person{
        private static int lastId;
        private final int id;
        @Setter
        private String name;
        @Setter
        private int age;

        static{
            lastId=0;
        }

        public Person(String name, int age){
            this(++lastId, name, age);
        }
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age){
        Person p=new Person(name, age);

        people.add(p);

        return "%d번 사람이 추가되었습니다.".formatted(p.getId());
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople(){

        return people;
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id){

        boolean removed=people.removeIf(person -> person.getId()==id);

        if(removed==false)
            return "%d번 사람이 존재하지 않습니다.".formatted(id);

        return "%d번 사람이 삭제되었습니다.".formatted(id);
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id, String name, int age){

        Person found=people.stream()
                .filter(p -> p.getId()==id) //해당 조건이 참인것만 필터링
                .findFirst() //필터링 결과가 하나만 남는데, 그 하나 남은걸 가져옴
                .orElse(null); //결과가 없으면 null을 반환

        if(found==null)
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
            
        found.setName(name);
        found.setAge(age);

        return "%d번 사람이 수정되었습니다.".formatted(id);

    }

    @GetMapping("home/cookie/increase")
    @ResponseBody
    public long showCookieIncrease(HttpServletRequest req, HttpServletResponse res){
        long countInCookie=0;

        if(req.getCookies() != null){
            countInCookie=Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(cookie -> cookie.getValue())
                    .mapToLong(Long::parseLong)
                    .findFirst()
                    .orElse(0);
        }


        res.addCookie(new Cookie("count", countInCookie+1+""));

        return countInCookie;
    }

    @GetMapping("home/user1")
    @ResponseBody
    public Member showUser1(){
        return memberService.findByUserName("user1");
    }
}
