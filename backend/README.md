# TAIMAKO New Backend Foundation
Fresh backend for the rebuilt app. This checkpoint contains only account/authentication foundations. Financial balances and transaction posting are intentionally disabled.

Roles: MEMBER, FLEXIBLE, ADMIN. Regular/Flexible credentials use secure bcrypt hashes; first-login change flag is stored in the database. Flexible accounts may link to a Regular account. Admin permissions are stored separately.
