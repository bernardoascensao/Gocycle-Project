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

import isel.sisinf.jpa.Bike.IBikeRepository;
import isel.sisinf.jpa.Customer.ICustomerRepository;
import isel.sisinf.jpa.JPAContext;
import isel.sisinf.jpa.Reservation.IReservationRepository;
import isel.sisinf.jpa.Store.IStoreRepository;
import isel.sisinf.model.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
        try (JPAContext context = new JPAContext()) {
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

            System.out.print("Cliente ou Gestor? (C/G) ");
            String atrdisc = scanner.nextLine();

            Customer customer = new Customer(name, address, email, phone, ccNumber, nationality, atrdisc.charAt(0));

            context.beginTransaction();
            context.getCustomerRepo().create(customer);
            context.commit();
            System.out.println("Customer created successfully with id: " + customer.getId());
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }
  
    private void listExistingBikes() {
        try (JPAContext context = new JPAContext()) {
            context.beginTransaction();
            Collection<Bike> bikes = context.getBikeRepo().findAll();
            context.commit();
            if (bikes.isEmpty()) {
                System.out.println("No bikes found.");
                return;
            }
            System.out.println("-----------Classic Bikes------------");
            for (Bike bike : bikes) {
               if (bike.getType() == 'C'){
                    System.out.printf("%-10s %-20s %-20s\n",
                            bike.getId(),
                            bike.getBrand(),
                            bike.getModel()
                    );
               }
            }
            System.out.println("-----------Electric Bikes-----------");
            for (Bike bike : bikes) {
                if (bike.getType() == 'E') {
                    System.out.printf("%-10s %-20s %-20s\n",
                            bike.getId(),
                            bike.getBrand(),
                            bike.getModel()
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing bikes: " + e.getMessage());
        }
        System.out.println();
    }

    private void checkBikeAvailability() {
        try (JPAContext context = new JPAContext()) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("ID da Bicicleta: ");
            int bikeId = scanner.nextInt();
            scanner.nextLine();  // consume the newline character left by nextInt()

            System.out.print("Data e Hora (dd/mm/aaaa hh:mm:ss): ");
            String dateTimeStr = scanner.nextLine();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date parsedDate = sdf.parse(dateTimeStr);
            Timestamp dateTime = new Timestamp(parsedDate.getTime());

            context.beginTransaction();
            boolean isAvailable = context.getBikeRepo().checkBikeAvailability(bikeId, dateTime);
            context.commit();
            if (isAvailable) {
                System.out.println("Bike available");
            } else {
                System.out.println("Bike not available");
            }
        } catch (Exception e) {
            System.out.println("Error checking bike availability: " + e.getMessage());
        }
    }

    private void obtainBookings() {
        try (JPAContext context = new JPAContext()) {
            System.out.println("Reservation List:");

            context.beginTransaction();
            Collection<Reservation> reservations = context.getReservationRepo().findAll();
            context.commit();

            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
                return;
            } else {
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                for(Reservation reservation : reservations){
                    System.out.println("-----------Reservation Details-----------");
                    System.out.println("ID: " + reservation.getId().getId());
                    System.out.println("ID da Loja: " + reservation.getStore().getId());
                    System.out.println("ID da Bicicleta: " + reservation.getBike().getId());
                    System.out.println("Número do Cliente: " + reservation.getCustomer().getId());
                    System.out.println("Data e Hora de Início: " + outputFormat.format(reservation.getStartDate()));
                    System.out.println("Data e Hora de Fim: " + outputFormat.format(reservation.getEndDate()));
                    System.out.println("Valor: " + reservation.getAmount());
                    System.out.println();
                }
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println("Error obtaining bookings: " + e.getMessage());
        }
    }

    private void makeBooking() {
        try (JPAContext context = new JPAContext()) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("ID da Loja: ");
            int storeId = scanner.nextInt();

            System.out.print("ID da Bicicleta: ");
            int bikeId = scanner.nextInt();

            System.out.print("Número do Cliente: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();  // consume the newline character left by nextInt()

            System.out.print("Data e Hora de Início (dd/mm/aaaa hh:mm:ss): ");
            String startDateStr = scanner.nextLine();

            System.out.print("Data e Hora de Fim (dd/mm/aaaa hh:mm:ss): ");
            String endDateStr = scanner.nextLine();

            System.out.print("Valor: ");
            double amount = scanner.nextDouble();

            context.beginTransaction();
            IBikeRepository bikeRepo = context.getBikeRepo();
            ICustomerRepository customerRepo = context.getCustomerRepo();
            IStoreRepository storeRepo = context.getStoreRepo();
            IReservationRepository reservationRepo = context.getReservationRepo();

            // Check if the bike exists
            Bike bike = bikeRepo.findByKey(bikeId);
            if (bike == null) {
                System.out.println("Bike not found.");
                return;
            }

            // Check if the customer exists
            Customer customer = customerRepo.findByKey(customerId);
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }

            // Check if the store exists
            Store store = storeRepo.findByKey(storeId);
            if (store == null) {
                System.out.println("Store not found.");
                return;
            }

            // Check if the reservation already exists
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Timestamp startDate = new Timestamp(dateFormat.parse(startDateStr).getTime());
            Timestamp endDate = new Timestamp(dateFormat.parse(endDateStr).getTime());
            reservationRepo.find(
                    "SELECT r FROM Reservation r WHERE r.bike = ?1 AND r.store = ?2 AND r.customer = ?3 AND r.startDate = ?4 AND r.endDate = ?5 AND r.amount = ?6",
                    bike, store, customer, startDate, endDate, amount
            );

            // Create the reservation and save it using a stored procedure
            Reservation reservation = new Reservation(store, bike, customer, startDateStr, endDateStr, amount);
            reservationRepo.create(reservation);
            context.flush();
            context.commit();

            System.out.println("Reservation created successfully.");
        } catch (Exception e) {
            System.out.println("Error making booking: " + e.getMessage());
        }
    }

    private void cancelBooking() {
        try (JPAContext context = new JPAContext()) {
            context.beginTransaction();
            IReservationRepository reservationRepo = context.getReservationRepo();

            Scanner scanner = new Scanner(System.in);

            System.out.print("ID da loja: ");
            int storeId = scanner.nextInt();

            System.out.print("ID da reserva: ");
            int reservationNumber = scanner.nextInt();

            // Verificar se a reserva existe
            ReservationId reservationId = new ReservationId(reservationNumber, storeId);
            Reservation reservation = reservationRepo.findByKey(reservationId);
            if (reservation == null) {
                System.out.println("Reserva não encontrada.");
                return;
            }

            // Cancel the reservation
            reservationRepo.delete(reservation);
            context.commit();
            System.out.println("Reservation deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error canceling booking: " + e.getMessage());
        }
    }

    private void about() {
        System.out.println("DAL version:"+ isel.sisinf.jpa.Dal.version());
        System.out.println("Core version:"+ isel.sisinf.model.Core.version());
        System.out.println("Grupo: Bernardo Ascensão, Maria Pedro, Constança Castro");
        System.out.println();
    }
}

public class App{
    public static void main(String[] args) throws Exception{
        UI.getInstance().Run();
    }
}