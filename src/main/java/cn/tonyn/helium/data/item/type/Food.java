package cn.tonyn.helium.data.item.type;

public class Food extends Item {
    public Food(){
        Type = Types.FOOD;
        _Tradable = true;
        _Eatable = true;
    }
}
