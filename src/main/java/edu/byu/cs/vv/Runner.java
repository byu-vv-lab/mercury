package edu.byu.cs.vv;

import edu.udel.cis.vsl.abc.FrontEnd;
import edu.udel.cis.vsl.civl.config.IF.CIVLConstants;
import edu.udel.cis.vsl.civl.kripke.common.CommonTraceStep;
import edu.udel.cis.vsl.civl.model.IF.Model;
import edu.udel.cis.vsl.civl.run.IF.ModelTranslator;
import edu.udel.cis.vsl.civl.run.IF.TracePlayer;
import edu.udel.cis.vsl.civl.run.common.CIVLCommandFactory;
import edu.udel.cis.vsl.civl.run.common.NormalCommandLine;
import edu.udel.cis.vsl.civl.semantics.IF.Transition;
import edu.udel.cis.vsl.civl.state.IF.State;
import edu.udel.cis.vsl.civl.transform.IF.TransformerFactory;
import edu.udel.cis.vsl.civl.transform.IF.Transforms;
import edu.udel.cis.vsl.gmc.*;
import static edu.udel.cis.vsl.civl.config.IF.CIVLConstants.seedO;

public class Runner {

    public static void main(String[] args) throws Exception {
        String[] opts = {"run", "src/resources/examples/mpi/diffusion1d.c"};

        Model model;
        TracePlayer player;
        Trace<Transition, State> result;
        {
            SortedMap <String, Option> definedOptions = new TreeMap<>();
            for (Option option : CIVLConstants.getAllOptions())
                definedOptions.put(option.name(), option);
            NormalCommandLine commandLine = (NormalCommandLine) CIVLCommandFactory.parseCommand(definedOptions.values(), opts);

            GMCConfiguration gmcConfig = commandLine.gmcConfig();
            GMCSection gmcSection = gmcConfig.getAnonymousSection();
            gmcSection.setScalarValue(seedO, "1454104962833");

            FrontEnd frontEnd = new FrontEnd();
            TransformerFactory transformerFactory =
                    Transforms.newTransformerFactory(frontEnd.getASTFactory());

            ModelTranslator modelTranslator = new ModelTranslator(
                    transformerFactory, frontEnd, gmcConfig, gmcSection,
                    commandLine.files(), commandLine.getCoreFileName());

            model = modelTranslator.translate();
            player = TracePlayer.randomPlayer(modelTranslator.gmcConfig, model,
                System.out, System.err);

            result = player.run();
        }
    }

}
