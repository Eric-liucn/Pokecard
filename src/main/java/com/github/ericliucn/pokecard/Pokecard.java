package com.github.ericliucn.pokecard;

import com.github.ericliucn.pokecard.commands.CommandManager;
import com.github.ericliucn.pokecard.config.ConfigLoader;
import com.github.ericliucn.pokecard.config.PCardConfig;
import com.github.ericliucn.pokecard.handler.EventHandler;
import com.github.ericliucn.pxielmonchinese.service.PokeCNService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

@Plugin(
        id = "pokecard",
        name = "Pokecard",
        description = "Pokecard",
        authors = {
                "Eric12324"
        },
        dependencies = {
                @Dependency(id = "pixelmonchinese")
        }
)
public class Pokecard {

    public static Pokecard instance;
    public static PokeCNService service;

    private ConfigLoader configLoader;
    private EventHandler eventHandler;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File file;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        configLoader = new ConfigLoader(file);
        new CommandManager();
        eventHandler = new EventHandler();
        service = Sponge.getServiceManager().provideUnchecked(PokeCNService.class);
    }

    public PCardConfig getConfig(){
        return this.configLoader.getConfig();
    }

    public void reload(){
        eventHandler.unregister();
        configLoader = new ConfigLoader(file);
        eventHandler = new EventHandler();
    }
}
