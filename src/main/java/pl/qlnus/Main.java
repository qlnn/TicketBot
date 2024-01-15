package pl.qlnus;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.qlnus.listener.ModalInteractionListener;
import pl.qlnus.listener.SlashCommandInteractionListener;
import pl.qlnus.listener.StringSelectInteractionListener;

import java.util.EventListener;
import java.util.stream.Stream;

public class Main implements EventListener {
    public static void main(String[] args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault("xdtokenbotaxdbeka")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
        jda.awaitReady();
        jda.updateCommands().addCommands(
                Commands.slash("ticket", "Stworz wiadomosc z ticketem!")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true)
                        .addOption(OptionType.CHANNEL, "channel", "Wybierz kanal na ktorym ma zostac wyslana wiadomosc", true)
        ).queue();
        Stream.of(
                new SlashCommandInteractionListener(),
                new StringSelectInteractionListener(),
                new ModalInteractionListener()
        ).forEach(jda::addEventListener);
    }
}