package collections;

public class HashMapDemo {

    public static void main(String[] args) {
        
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>(8);

        map.put("A", 10);
        map.put("B", 20);
        map.put("A", 50);

        System.out.println(map.get("A")); // 50
        System.out.println(map.get("B")); // 20
        System.out.println(map.get("C")); // null
    }

}
