package com.jordan.peopleofstarwars

import com.apollographql.apollo3.ApolloClient

//Create a new ApolloClient
val apolloClient = ApolloClient.Builder()
    .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index/graphql")
    .build()
