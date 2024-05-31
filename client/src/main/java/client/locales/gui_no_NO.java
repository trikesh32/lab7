package client.locales;

import java.util.ListResourceBundle;

public class gui_no_NO extends ListResourceBundle {
    public Object[][] getContents(){
        return contents;
    }
    private Object[][] contents = {
    {"Error", "Feil"},
    {"Timeout", "Tidsavbrudd"},
    {"UnavailableError", "Tjenesten er utilgjengelig"},
    {"ServerDoesntResponse", "Serveren svarer ikke"},
    {"ServerIOException", "Vet ikke hvordan jeg skal kalle denne feilen"},
    {"InvalidCredentials", "Sjekk opplysningene dine"},
    {"AuthTitle", "Autorisering"},
    {"LoginField", "Brukernavn"},
    {"PasswordField", "Passord"},
    {"SignUpButton", "Registrer deg"},
    {"UserNotFound", "Bruker ikke funnet"},
    {"PasswordIncorrect", "Feil passord"},
    {"UserAlreadyExists", "Bruker eksisterer allerede"},
    {"RegisterSuccess", "Registrering fullført"},
    {"AuthSuccess", "Autorisering fullført"},
    {"Info", "Informasjon"},
    {"NotValidData", "Ugyldige data"},
    {"DBError", "Databasefeil"},
    {"ObjectNotFound", "Objekt ikke funnet"},
    {"Forbidden", "Tilgang nektet"},
    {"InfoResult", "Type: {0}\nAntall elementer: {1}\nSiste lagringsdato: {2}\nSiste initialiseringsdato: {3}"},
    {"EditTitle", "Redigering"},
    {"Cancel", "Avbryt"},
    {"NumberFormatException", "Tallformat feil"},
    {"UserLabel", "Bruker"},
    {"Exit", "Avslutt"},
    {"LogOut", "Logg ut"},
    {"Help", "Hjelp"},
    {"Add", "Legg til"},
    {"Update", "Oppdater"},
    {"RemoveByID", "Fjern etter ID"},
    {"Clear", "Tøm"},
    {"ExecuteScript", "Kjør skript"},
    {"RemoveLower", "Fjern lavere"},
    {"SumOfCapacity", "Kapasitetssum"},
    {"FilterByCapacity", "Filtrer etter kapasitet"},
    {"FilterLessThanType", "Filtrer etter type"},
    {"TableTab", "Tabell"},
    {"VisualTab", "Visualisering"},
    {"Owner", "Eier"},
    {"Name", "Navn"},
    {"CreationDate", "Opprettelsesdato"},
    {"EnginePower", "Motorkraft"},
    {"Capacity", "Kapasitet"},
    {"VehicleType", "Kjøretøytype"},
    {"FuelType", "Drivstofftype"},
    {"EditTitle", "Redigering"},
    {"Cancel", "Avbryt"},
    {"CommandNotFound", "Kommando ikke funnet"},
    {"CheckScriptErr", "Sjekk skriptet ditt"},
    {"ScriptExecutionErr", "Feil ved kjøring av skript"},
    {"HelpResult", "add {element}: legger til et nytt element i samlingen\n" +
            "clear: tømmer samlingen\n" +
            "execute_script filename: kjører skript fra den angitte filen\n" +
            "exit: avslutter programmet uten lagring\n" +
            "filter_by_capacity: viser elementer hvor feltet kapasitet er lik det angitte\n" +
            "filter_less_than_type: viser elementer hvor verdien i feltet kjøretøytype er mindre enn det angitte\n" +
            "help: viser informasjon om tilgjengelige kommandoer\n" +
            "info: viser informasjon om samlingen\n" +
            "remove_by_id id: fjerner element etter ID\n" +
            "remove_last: fjerner siste element\n" +
            "remove_lower {element}: fjerner alle elementer som er mindre enn det angitte\n" +
            "show: viser informasjon om samlingen\n" +
            "sort: sorterer samlingen etter elementnavn\n" +
            "sum_of_capacity: viser summen av alle feltene kapasitet\n" +
            "update id {element}: oppdaterer verdien av elementet"},
    {"Success", "Suksess"},
    {"UpdateSuc", "Oppdatering vellykket"},
    {"ClearSuc", "Tømming vellykket"},
    {"RemoveById", "Fjerning etter ID"},
    {"RemoveByIDSuc", "Vellykket"},
    {"ScriptExecutionSuc", "Vellykket"},
    {"Result", "Resultat"},
    {"WrongType", "Feil type"},
    {"FilterLessThanTypeResult", "Funnet objekter"},
    {"WrongNumber", "Feil nummer"}
    };
}
