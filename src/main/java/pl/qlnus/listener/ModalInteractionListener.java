package pl.qlnus.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;

public class ModalInteractionListener extends ListenerAdapter {
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (event.getModalId().equalsIgnoreCase("ticket")) {
            String nick = event.getValue("nick").getAsString();
            String information = event.getValue("information").getAsString();
            event.getGuild().getCategoryById("1196530555601289397")
                    .createTextChannel(event.getMember().getUser().getEffectiveName() + "-ticket")
                    .setTopic(event.getMember().getId())
                    .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL), null)
                    .addPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .queue(textChannel -> {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setFooter("ticket");
                        embedBuilder.setColor(Color.BLACK);
                        embedBuilder.addField("Nick gracza: ", " " + nick, false);
                        embedBuilder.addField("Powod zgłoszenia: ", " " + information, false);
                        embedBuilder.addField("", "**Aby zarzadzac ticketem nacisnij na reakcje!**", false);
                        textChannel.sendMessage("<@" + event.getMember().getId() + ">").queue();
                        textChannel.sendMessageEmbeds(embedBuilder.build()).addActionRow(StringSelectMenu.create("ticket").setPlaceholder("Zarządzaj zgłoszeniem!")
                                .addOptions(
                                        SelectOption.of("Zamknij zgloszenie", "close"),
                                        SelectOption.of("Zapisz logi", "logs")).build()).queue(message -> message.pin().queue());
                    });
            event.reply("Twoje zgloszenie zostalo stworzone").setEphemeral(true).queue();
        }
    }
}
