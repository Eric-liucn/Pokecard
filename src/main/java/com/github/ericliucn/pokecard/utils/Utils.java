package com.github.ericliucn.pokecard.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Utils {

    public static Text toText(String string){
        return TextSerializers.FORMATTING_CODE.deserialize(string);
    }
}
