package sample;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<BodyPart> body;

    public Snake(){
        this.body = new ArrayList<>();
        add(new BodyPart(10, 10));
        add(new BodyPart(10, 10));
        add(new BodyPart(10, 10));
    }

    public void grow(){
        body.add(new BodyPart(-1,-1));
    }

    public void add(BodyPart bp){
        body.add(bp);
    }

    public BodyPart get(int i){
        return body.get(i);
    }

    public int size(){
        return body.size();
    }

    public List<BodyPart> getBody() {
        return body;
    }
}
