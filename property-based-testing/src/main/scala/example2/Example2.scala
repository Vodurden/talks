package com.reagroup.resi.propertybasedtesttalk.example2

// Example: Get the listing id out of a CsvRow

/** Represents a single row in a CSV of this format:
  *
  *     activeDate, numActiveDays, listingId
  *     20/10/2017,             7, 133333333
  *     21/10/2017,             7, 144444444
  *     22/10/2017,             7, 222222222
  *     28/10/2017,             1, 155555555
  */
/*case class CsvRow(startDate: LocalDate, numActiveDays: Int, listingId: String)

/** Represents a realestate.com.au listing id.
  *
  * The first digit of a listing id denotes it's type.
  *
  * Currently we only support the following types:
  *
  *     1 - Residential
  *     6 - Project Profile
  */
sealed trait ListingId
case class ResidentialListingId(id: String) extends ListingId
case class ProjectProfileListingId(id: String) extends ListingId

object ListingId {
  /** Get a ListingId or an error from a CsvRow.
    *
    * Requirements:
    *
    * - The listing id must start with a "1" or "6"
    *
    * If any requirements are not met an error string will be returned
    */
  def fromCsvRow(csvRow: CsvRow): Either[String, ListingId] = {
    if(csvRow.listingId.isEmpty) {
      Left("listing id must not be empty")
    } else if(csvRow.listingId.exists(c => !c.isDigit)) {
      Left(s"listing id must be numeric. Given: ${id}")
    } else if(id.startsWith("1")) {
      Right(ResidentialListingId(id))
    } else if(id.startsWith("6")) {
      Right(ProjectProfileListingId(id))
    } else {
      Left(s"listing id must start with 1 or 6. Given: ${id}")
    }
  }
}
 */
