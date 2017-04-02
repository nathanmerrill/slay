package com.ppcg.slay;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

    @Parameter(names = {"-i", "--iterations"}, description = "Number of games to run")
    public int iterations = 50;

    @Parameter(names = {"-c", "--compile"}, description = "Whether submissions should be compiled")
    public boolean shouldCompile = true;

    @Parameter(names = {"-e", "--exclude"}, description = "Languages that should be excluded.  Use 'other' for languages without built-in support.  You can prefix a language with `non` to exclude everything but that language", variableArity = true)
    public List<String> excludedLanguages = new ArrayList<>();

    @Parameter(names = {"-d", "--directory"}, description = "Directory for submissions")
    public String directory = "submissions";

    @Parameter(names = {"-D", "--download"}, description = "Whether new submissions should be downloaded")
    public boolean shouldDownload = false;


}
