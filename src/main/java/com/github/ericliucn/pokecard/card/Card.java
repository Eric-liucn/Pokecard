package com.github.ericliucn.pokecard.card;

import com.github.ericliucn.pokecard.Pokecard;
import com.github.ericliucn.pokecard.utils.Utils;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.text.Text;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private final Pokemon pokemon;
    private ItemStack stack;
    public static final String POKEDATA = "pokemonData";

    public Card(Pokemon pokemon){
        this.pokemon = pokemon;
        this.setStack();
        this.setLore();
    }

    private void setStack(){
        this.stack = ItemPixelmonSprite.getPhoto(pokemon);
        NBTTagCompound tagCompound = this.stack.getTagCompound();
        if (tagCompound != null){
            tagCompound.setTag(POKEDATA, pokemon.writeToNBT(new NBTTagCompound()));
            this.stack.setTagCompound(tagCompound);
        }
    }

    private void setLore(){
        org.spongepowered.api.item.inventory.ItemStack itemStack = ItemStackUtil.fromNative(this.stack);
        EVStore evStore = pokemon.getEVs();
        IVStore ivStore = pokemon.getIVs();
        List<Text> texts = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        for (String s:Pokecard.instance.getConfig().cardLore){
            stringBuilder.append(s);
        }
        String loreString = stringBuilder.toString();
        int totalEV = evStore.speed + evStore.hp + evStore.specialDefence + evStore.specialDefence + evStore.attack + evStore.defence;
        int totalIV = ivStore.speed + ivStore.hp + ivStore.specialDefence + ivStore.specialDefence + ivStore.attack + ivStore.defence;

        String loreStringRep = loreString
                .replace("%level%", String.valueOf(pokemon.getLevel()))
                .replace("%gender%", Pokecard.service.getGender(pokemon.getGender()))
                .replace("%nature%", Pokecard.service.getPixelmonNatureName(pokemon.getNature().getName()))
                .replace("%ability%", Pokecard.service.getPixelmonAbilityName(pokemon.getAbilityName()))
                .replace("%growth%", Pokecard.service.getGrowth(pokemon.getGrowth()))
                .replace("%shiny%", pokemon.isShiny() ? "是":"否")
                .replace("%attack_1%", pokemon.getMoveset().attacks[0] == null? "无": Pokecard.service.getAttackName(pokemon.getMoveset().attacks[0].getMove().getAttackName()))
                .replace("%attack_2%", pokemon.getMoveset().attacks[1] == null? "无": Pokecard.service.getAttackName(pokemon.getMoveset().attacks[1].getMove().getAttackName()))
                .replace("%attack_3%", pokemon.getMoveset().attacks[2] == null? "无": Pokecard.service.getAttackName(pokemon.getMoveset().attacks[2].getMove().getAttackName()))
                .replace("%attack_4%", pokemon.getMoveset().attacks[3] == null? "无": Pokecard.service.getAttackName(pokemon.getMoveset().attacks[3].getMove().getAttackName()))
                .replace("%ev_defend%", String.valueOf(evStore.defence))
                .replace("%ev_attack%", String.valueOf(evStore.attack))
                .replace("%ev_sp_defend%", String.valueOf(evStore.specialDefence))
                .replace("%ev_sp_attack%", String.valueOf(evStore.specialAttack))
                .replace("%ev_health%", String.valueOf(evStore.hp))
                .replace("%ev_speed%", String.valueOf(evStore.speed))
                .replace("%ev_percent%", (totalEV*100/510) + "%")
                .replace("%iv_defend%", String.valueOf(ivStore.defence))
                .replace("%iv_attack%", String.valueOf(ivStore.attack))
                .replace("%iv_sp_defend%", String.valueOf(ivStore.specialDefence))
                .replace("%iv_sp_attack%", String.valueOf(ivStore.specialAttack))
                .replace("%iv_health%", String.valueOf(ivStore.hp))
                .replace("%iv_speed%", String.valueOf(ivStore.speed))
                .replace("%iv_percent%", (totalIV*100/186) + "%");

        Text lore = Utils.toText(loreStringRep).replace("\n", Text.NEW_LINE);
        texts.add(lore);

        itemStack.offer(Keys.ITEM_LORE, texts);
    }

    public ItemStack getStack() {
        return stack;
    }
}
