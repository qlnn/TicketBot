package pl.qlnus.listener;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class StringSelectInteractionListener extends ListenerAdapter {
    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        if (event.getComponentId().equalsIgnoreCase("ticket")) {
            switch (event.getValues().get(0)) {
                case "discord" -> {
                    event.replyModal(openModal()).queue();
                    break;
                }
                case "logs" -> {

                }
                case "close" -> {
                    event.reply("Trwa zamykanie zgloszenia....").setEphemeral(true).queue();
                    event.getChannel().delete().queueAfter(5L, TimeUnit.SECONDS);
                }
            }
        }
    }
    private Modal openModal() {
        TextInput nick = TextInput.create("nick", "Podaj swoj nick z serwera", TextInputStyle.SHORT)
                .setPlaceholder("Podaj swoj nick z serwera")
                .setMinLength(3)
                .setMaxLength(16)
                .build();
        TextInput information = TextInput.create("information", "Podaj nam informacje dot. zgloszenia", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Podaj nam informacje dot. zgloszenia")
                .setMinLength(10)
                .setMaxLength(200)
                .build();
        return Modal.create("ticket", "TicketBot")
                .addActionRow(nick)
                .addActionRow(information).build();
    }
}
