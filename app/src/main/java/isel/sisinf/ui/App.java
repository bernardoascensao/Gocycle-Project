/*
MIT License

Copyright (c) 2024, Nuno Datia, Matilde Pato, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.ui;

import isel.sisinf.jpa.CustomerRepository;
import isel.sisinf.model.Customer;

import java.util.Scanner;
import java.util.HashMap;

interface DbWorker
{
    void doWork();
}
class UI
{
    private enum Option
    {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        createCostumer,
        listExistingBikes,
        checkBikeAvailability,
        obtainBookings,
        makeBooking,
        cancelBooking,
        about
    }
    private static UI __instance = null;
  
    private HashMap<Option,DbWorker> __dbMethods;
    private CustomerRepository customerRepository;

    private UI()
    {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option,DbWorker>();
        __dbMethods.put(Option.createCostumer, () -> UI.this.createCostumer());
        __dbMethods.put(Option.listExistingBikes, () -> UI.this.listExistingBikes()); 
        __dbMethods.put(Option.checkBikeAvailability, () -> UI.this.checkBikeAvailability());
        __dbMethods.put(Option.obtainBookings, new DbWorker() {public void doWork() {UI.this.obtainBookings();}});
        __dbMethods.put(Option.makeBooking, new DbWorker() {public void doWork() {UI.this.makeBooking();}});
        __dbMethods.put(Option.cancelBooking, new DbWorker() {public void doWork() {UI.this.cancelBooking();}});
        __dbMethods.put(Option.about, new DbWorker() {public void doWork() {UI.this.about();}});

        customerRepository = new CustomerRepository();

    }

    public static UI getInstance()
    {
        // DO NOT CHANGE ANYTHING!
        if(__instance == null)
        {
            __instance = new UI();
        }
        return __instance;
    }

    private Option DisplayMenu()
    {
        Option option = Option.Unknown;
        Scanner s = new Scanner(System.in); //Scanner closes System.in if you call close(). Don't do it
        try
        {
            // DO NOT CHANGE ANYTHING!
            System.out.println("Bicycle reservation");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Create Costumer");
            System.out.println("3. List Existing Bikes");
            System.out.println("4. Check Bike Availability");
            System.out.println("5. Current Bookings");
            System.out.println("6. Make a booking");
            System.out.println("7. Cancel Booking");
            System.out.println("8. About");
            System.out.print(">");
            int result = s.nextInt();
            option = Option.values()[result];
        }
        catch(RuntimeException ex)
        {
            //nothing to do.
        }
        
        return option;

    }
    private static void clearConsole() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");
    }

    public void Run() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        Option userInput;
        do
        {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try
            {
                __dbMethods.get(userInput).doWork();
                System.in.read();
            }
            catch(NullPointerException ex)
            {
                //Nothing to do. The option was not a valid one. Read another.
            }

        }while(userInput!=Option.Exit);

        customerRepository.close();
    }

    /**
    To implement from this point forward. Do not need to change the code above.
    -------------------------------------------------------------------------------     
        IMPORTANT:
    --- DO NOT MOVE IN THE CODE ABOVE. JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
    --- Other Methods and properties can be added to support implementation -------
    -------------------------------------------------------------------------------
    
    */

    private static final int TAB_SIZE = 24;

    private void createCostumer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome: ");
        String name = scanner.nextLine();

        System.out.print("Morada: ");
        String address = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Número de telefone: ");
        String phone = scanner.nextLine();

        System.out.print("Número de CC ou passaporte: ");
        String ccNumber = scanner.nextLine();

        System.out.print("Nacionalidade: ");
        String nationality = scanner.nextLine();

        Customer customer = new Customer(name, address, email, phone, ccNumber, nationality);
        customerRepository.saveCustomer(customer);
        System.out.println("Customer created successfully.");

        // Adicionar o código para inserir esses dados no sistema
        // Por exemplo, chamando um método no Dal para salvar os dados do cliente no banco de dados

        System.out.println("createCostumer()");
    }
  
    private void listExistingBikes()
    {
        // Adicionar o código para recuperar a lista de bicicletas do sistema
        // Por exemplo, chamando um método no Dal para obter os dados das bicicletas do banco de dados

        System.out.println("Lista de Bicicletas:");
        // Iterar e imprimir a lista de bicicletas

        System.out.println("listExistingBikes()");
    }

    private void checkBikeAvailability()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID da Bicicleta: ");
        String bikeId = scanner.nextLine();

        System.out.print("Data e Hora (dd/mm/aa hh:mm:ss): ");
        String dateTime = scanner.nextLine();

        // Adicionar o código para verificar a disponibilidade da bicicleta
        // Por exemplo, chamando um método no Dal para verificar se a bicicleta está disponível no banco de dados

        System.out.println("checkBikeAvailability()");
    }

    private void obtainBookings() {
        // Adicionar o código para recuperar a lista de reservas do sistema
        // Por exemplo, chamando um método no Dal para obter os dados das reservas do banco de dados

        System.out.println("Lista de Reservas:");
        // Iterar e imprimir a lista de reservas

        System.out.println("obtainBookings()");
    }

    private void makeBooking()
    {
        // Adicionar o código para recuperar a lista de reservas do sistema
        // Por exemplo, chamando um método no Dal para obter os dados das reservas do banco de dados

        System.out.println("Lista de Reservas:");
        // Iterar e imprimir a lista de reservas

        System.out.println("makeBooking()");
    }

    private void cancelBooking()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Número da Reserva: ");
        String bookingNumber = scanner.nextLine();

        // Adicionar o código para cancelar a reserva no sistema
        // Por exemplo, chamando um método no Dal para remover a reserva no banco de dados

        System.out.println("cancelBooking");
    }
    private void about()
    {
        // TODO: Add your Group ID & member names
        System.out.println("DAL version:"+ isel.sisinf.jpa.Dal.version());
        System.out.println("Core version:"+ isel.sisinf.model.Core.version());
        System.out.println("Grupo: Bernardo Ascensão, Maria Pedro, Constança Castro");
        
    }
}

public class App{
    public static void main(String[] args) throws Exception{
        UI.getInstance().Run();
    }
}