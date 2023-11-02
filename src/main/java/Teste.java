import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Teste {

    public static void main(String[] args) {
        Map map = new HashMap<>();

        IntStream.range(0,10).forEach(e->map.put("Jogador#"+e, e));
        Set<Entry<String, Integer>> set =map.entrySet();

        for(Entry player: set){
            player.getKey();
            map.put(player.getKey(), (int)player.getValue()+5);
        }
        System.out.println(map);

        var string = ("PedroFerreiraAleixoLois"+String.valueOf(System.currentTimeMillis())).getBytes();
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.nameUUIDFromBytes(("PedroFerreiraAleixoLois"+String.valueOf(System.currentTimeMillis())).getBytes()));




    }

    public static class Classe{

        public void metodoManeiro(){
            System.out.println("Sou maneiro!");
        }
    }
    
    }

