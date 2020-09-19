package com.github.ericliucn.pokecard.config;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    private final File rootFile;
    private PCardConfig config;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;

    public ConfigLoader(File rootFile){
        this.rootFile = rootFile;
        File configFile = new File(rootFile, "pokecard.conf");
        this.loader = HoconConfigurationLoader.builder().setFile(configFile).build();
        createDir();
        loadConfig();
    }

    private void createDir(){
        if (!this.rootFile.exists()){
            this.rootFile.mkdir();
        }
    }

    private void loadConfig(){
        try {
            CommentedConfigurationNode node = loader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
            this.config = node.getValue(TypeToken.of(PCardConfig.class), new PCardConfig());
            loader.save(node);
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public PCardConfig getConfig() {
        return config;
    }
}
