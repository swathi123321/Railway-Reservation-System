// Sample train data
const trains = [
  { id: "T1", number: "12627", name: "Karnataka Express", source: "Bengaluru", destination: "Delhi", date: "2025-12-03", seats: 100 },
  { id: "T2", number: "16590", name: "Rani Chennamma", source: "Bengaluru", destination: "Belagavi", date: "2025-12-02", seats: 50 }
];

const bookings = [];

function searchTrains() {
  const source = document.getElementById("source").value;
  const destination = document.getElementById("destination").value;
  const date = document.getElementById("date").value;

  const results = trains.filter(t =>
    t.source.toLowerCase() === source.toLowerCase() &&
    t.destination.toLowerCase() === destination.toLowerCase() &&
    t.date === date
  );

  let output = results.length ? "" : "No trains found.";
  results.forEach(t => {
    output += `Train ID: ${t.id}, ${t.name} (${t.number}), Seats: ${t.seats}<br>`;
  });
  document.getElementById("searchResults").innerHTML = output;
}

function bookTickets() {
  const trainId = document.getElementById("trainId").value;
  const seats = parseInt(document.getElementById("seats").value);

  const train = trains.find(t => t.id === trainId);
  if (!train) {
    document.getElementById("bookingResult").innerHTML = "Train not found.";
    return;
  }

  if (train.seats >= seats) {
    train.seats -= seats;
    const pnr = "PNR-" + Math.random().toString(36).substring(2, 10).toUpperCase();
    bookings.push({ pnr, trainId, seats, cancelled: false });
    document.getElementById("bookingResult").innerHTML = `Booking successful! PNR: ${pnr}, Remaining seats: ${train.seats}`;
  } else {
    document.getElementById("bookingResult").innerHTML = "Not enough seats available.";
  }
}

function cancelBooking() {
  const pnr = document.getElementById("pnr").value;
  const booking = bookings.find(b => b.pnr === pnr);

  if (!booking) {
    document.getElementById("cancelResult").innerHTML = "Booking not found.";
    return;
  }

  if (booking.cancelled) {
    document.getElementById("cancelResult").innerHTML = "Booking already cancelled.";
    return;
  }

  booking.cancelled = true;
  const train = trains.find(t => t.id === booking.trainId);
  train.seats += booking.seats;
  document.getElementById("cancelResult").innerHTML = `Booking cancelled. Seats restored: ${train.seats}`;
}
