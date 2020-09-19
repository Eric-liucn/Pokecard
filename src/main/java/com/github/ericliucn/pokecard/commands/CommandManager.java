package com.github.ericliucn.pokecard.commands;

import com.github.ericliucn.pokecard.Pokecard;
import com.github.ericliucn.pokecard.card.Card;
import com.github.ericliucn.pokecard.utils.Utils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandManager {

    private static final List<String> INDEX = new ArrayList<String>()
    {{
       add("1");
       add("2");
       add("3");
       add("4");
       add("5");
       add("6");
    }};

    public static final CommandSpec CREATE = CommandSpec
            .builder()
            .executor((src, args) -> {
                if (src instanceof Player) {
                    EntityPlayerMP playerMP = ((EntityPlayerMP) src);
                    PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(playerMP);

                    if (Arrays.stream(playerPartyStorage.getAll()).filter(Objects::nonNull).count() <= 1){
                        src.sendMessage(Utils.toText("&4你必须至少保留一个宝可梦"));
                        return CommandResult.success();
                    }

                    args.<Integer>getOne("index").ifPresent(index -> {
                        if (!INDEX.contains(String.valueOf(index))){
                            src.sendMessage(Utils.toText("&4格子数必须在1-6之间！"));
                            return;
                        }
                        Pokemon pokemon = playerPartyStorage.get(index - 1);
                        if (pokemon != null){
                            Card card = new Card(pokemon);
                            playerMP.inventory.addItemStackToInventory(card.getStack());
                            pokemon.retrieve();
                            playerPartyStorage.set(index - 1, null);
                        }else {
                            src.sendMessage(Utils.toText("&4这个格子是空的！"));
                        }
                    });
                }
                return CommandResult.success();
            })
            .arguments(GenericArguments.withSuggestions(GenericArguments.integer(Text.of("index")), INDEX))
            .build();

    public static final CommandSpec RELOAD = CommandSpec
            .builder()
            .executor((src, args) -> {
                Pokecard.instance.reload();
                return CommandResult.success();
            })
            .build();

    public static final CommandSpec BASE = CommandSpec
            .builder()
            .executor((src, args) -> {
                EntityPlayerMP playerMP = ((EntityPlayerMP) src);
                PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(playerMP);
                Pokemon pokemon = playerPartyStorage.get(0);
                Text text = Text.builder().append(Text.of("test")).append(Text.NEW_LINE).append(Text.of("12324")).build();
                src.sendMessage(text);
                return CommandResult.success();
            })
            .child(RELOAD, "reload")
            .child(CREATE, "create")
            .build();

    public CommandManager(){
        Sponge.getCommandManager().register(Pokecard.instance, BASE, "pokecard");
    }

}
