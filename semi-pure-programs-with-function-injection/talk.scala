case class VerifiableBooking (
  rowNumber: Int,
  status: BookingStatus,
  startDate: StartDate,
  bookingPeriod: BookingPeriod,
  listingId: ListingId,
  location: Location,
  qcCompleted: Boolean)

trait ListingIdService {
  def verifyListingId(listingId: ListingId): Future[VerifiedListingId] = ???
}

trait LocationService {
  def verifyLocation(location: Location): Future[VerifiedLocation] = ???
}

val bookingGenerator: Gen[VerifiableBooking] = for {
  rowNumber <- Gen.int
  status <- giveMeABookingStatus
  startDate <- Gen.date
} yield VerifiableBooking(rowNumber, status, startDate)

bookingGenerator.sample

object BookingServiceTest {
  "it succeeds when both validation succed" >> {
    Prop.forEach(bookingGenerator) { booking =>
      val verifyListing = (id: ListingId) => Id(VerifiedListingId(...))
      val verifyLocation = (id: Location) => Id(VerifiedLocation(..verifyLocation.))
      val bookingService = BookingService(verifiyListing, verifyLocation)

      val result = bookingService.verifyBooking(booking)

      Assert.Something(result)
    }
  }
}

new BookingService[Future[Int]](ListingIdService.verifiyListing, LocationService.verifyLocation).verifyBooking(...)

class BookingService[F[_]](
  verifyListingId: ListingId => F[VerifiedListingId], verifyLocation: Location => F[VerifiedLocation]
)(implicit f: Functor[F]): F[VerifiedBooking]
  def verifyBooking(booking: VerifiableBooking): VerifiedBooking = {
    if(booking.status != Booked) return Right()

    val verifiedListingIdOrError =
      verifyListingId(booking.listingId)
    val verifiedLocationOrError =
      verifyLocation(booking.location)

    verifiedListingIdOrError.flatMap { value =>
      verifyLocation.map { value2 =>
        VerifiedBooking(
          b.status, b.startDate, b.bookingPeriod,
          listing, location,
          b.qcCompleted
        )
      }
    }

  }
}
