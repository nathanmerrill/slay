package com.ppcg.slay;

import com.beust.jcommander.JCommander;
import com.ppcg.kothcomm.game.GameManager;
import com.ppcg.kothcomm.game.PlayerType;
import com.ppcg.kothcomm.game.TournamentRunner;
import com.ppcg.kothcomm.game.scoreboards.MamScoreboard;
import com.ppcg.kothcomm.game.tournaments.AdjacentPlayerProvider;
import com.ppcg.kothcomm.loader.Compiler;
import com.ppcg.kothcomm.loader.Downloader;
import com.ppcg.kothcomm.loader.LanguageLoader;
import com.ppcg.kothcomm.loader.SubmissionFileManager;
import com.ppcg.kothcomm.loader.languages.JavaLoader;
import com.ppcg.kothcomm.loader.languages.PipeLoader;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        JCommander commander = new JCommander(arguments);
        commander.setCaseSensitiveOptions(false);
        commander.parse(args);
        execute(arguments);
    }



    private static void run(List<PlayerType<Player>> players, int numIterations){
        GameManager<Player> manager = new GameManager<>(Slay::new);
        manager.register(players);
        manager.playerCount(4);
        TournamentRunner<Player> runner = new TournamentRunner<>(new AdjacentPlayerProvider<>(manager), MamScoreboard::new);
        System.out.println(runner.run(numIterations, System.out).scoreTable());
    }

    private static List<PlayerType<Player>> loadPlayers(List<String> excludedLanguages, SubmissionFileManager fileManager){
        LanguageLoader<Player> loader = new LanguageLoader<>();
        for (String language: excludedLanguages){
            if (language.startsWith("non")){
                loader.include(language.substring(3));
            } else {
                loader.exclude(language);
            }
        }
        loader.addLoader(new JavaLoader<>(fileManager));
        loader.addLoader(new PipeLoader<>(fileManager, PipePlayer::new));
        return loader.loadAll(Player.class);
    }

    private static void compile(SubmissionFileManager fileManager){
        Compiler compiler = new Compiler(fileManager);
        compiler.compile();
    }

    private static void download(SubmissionFileManager fileManager){
        Downloader downloader = new Downloader(fileManager, 91566);
        downloader.downloadQuestions();
    }

}
