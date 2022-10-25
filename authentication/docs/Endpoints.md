Authentication:
localhost8082:/public/register -> POST -> Registers user
localhost8082:/public/login -> POST -> Logs in user

Reviews:
localhost8082:/reviews -> POST -> Creates a review
localhost8082:/reviews/{id} -> GET -> Find a review by their id
localhost8082:/reviews/{id} -> PATCH -> Partially updates an existing review
localhost8082:/reviews/{id}/upvote -> PATCH -> Vote a like in a review
localhost8082:/reviews/{id}/downvote -> PATCH -> Vote a dislike in a review
localhost8082:/reviews/product/{productId}/date -> GET -> Gets approved reviews for a product sorted by date
localhost8082:/reviews/product/{productId}/date/votes -> GET -> Gets approved reviews for a product sorted by date and number of votes
localhost8082:/reviews/pending -> GET -> Gets pending reviews
localhost8082:/reviews/customer/{id} -> GET -> Gets reviews of a client
localhost8082:/reviews/{reviewId} -> DELETE -> Customer can delete one of his reviews - available if reviews has no votes

Products:
localhost8082:/products -> GET -> Shows catalog of products
localhost8082:/products -> POST -> Create a product
localhost8082:/products/{productId}/photos -> POST -> Uploads a set of photos of a product
localhost8082:/products/{productId}/photo -> POST -> Uploads a photo of a product
localhost8082:/products/{productId} -> GET -> Search for a product by his id
localhost8082:/products/{productId}/rating -> GET -> Search for a rating of a product
localhost8082:/products/name/{productName} -> GET -> Search for a product by his name

