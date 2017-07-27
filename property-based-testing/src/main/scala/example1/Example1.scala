package com.reagroup.resi.propertybasedtesttalk.example1

case class CsvRow(
  status: String,
  bookingPeriod: String,
  listingId: String,
  postcode: String
)

object CsvRow {
  /** Returns true if all rules are satisfied, false otherwise.
    *
    * - Rule: status must be "Booked" or "On Hold"
    * - Rule: listingId must be a number
    * - Rule: listingId must start with "1" or "6"
    * - Rule: booking period must be "7" or "14"
    * - Rule: postcode must be a number
    * - Bonus Rule: If status is "On Hold" no "startDate" should be given?
    */
  def isValidRow(row: CsvRow): Boolean = {
    isValidStatus(row.status) &&
      isValidBookingPeriod(row.bookingPeriod) &&
      isValidListingId(row.listingId) &&
      isValidPostcode(row.postcode)
  }

  def isValidStatus(status: String): Boolean = {
    status == "Booked" || status == "On Hold"
  }

  def isValidBookingPeriod(bookingPeriod: String): Boolean = {
    bookingPeriod == "7" || bookingPeriod == "14"
  }

  def isValidListingId(listingId: String): Boolean = {
    isNumber(listingId) && (listingId.startsWith("1") || listingId.startsWith("6"))
  }

  def isValidPostcode(postcode: String): Boolean = {
    isNumber(postcode)
  }

  def isNumber(s: String): Boolean = {
    s.forall(c => c.isDigit)
  }
}
