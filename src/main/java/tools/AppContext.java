package tools;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private Map<Class<?>,Object> context=new HashMap<>();

    public void add(Class<?> _class,Object object)
    {
        if(!context.containsKey(_class))
        {
            context.put(_class,object);
        }
    }
    public Object get(Class<?> _class)
    {
        return context.get(_class);
    }

}
