package com.github.ericliucn.pokecard.handler;

import com.github.ericliucn.pokecard.card.Card;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

    public EventHandler(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void unregister(){
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event){
        if (event.getEntityPlayer().getEntityWorld().isRemote) return;
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof ItemPixelmonSprite && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(Card.POKEDATA)){
            NBTTagCompound tagCompound = itemStack.getTagCompound().getCompoundTag(Card.POKEDATA);
            Pokemon pokemon  = Pixelmon.pokemonFactory.create(tagCompound);
            EntityPlayerMP playerMP = (EntityPlayerMP) event.getEntityPlayer();
            PlayerPartyStorage partyStorage = Pixelmon.storageManager.getParty(playerMP);
            partyStorage.add(pokemon);
            itemStack.shrink(1);
        }
    }
}
