import React from "react";


const ProfileCard = ( {user, count} ) => {
  return (
    <>
      <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg mt-16">
        <div className="px-6">
          <div className="flex flex-wrap justify-center">
            <div className="w-full px-4 text-center mt-0">
              <h1 className="text-3xl font-bold pt-12">Summary</h1>

              <div className="flex justify-center py-4 lg:pt-4 pt-8">
                
                <div className="mr-4 p-3 text-center">
                  <span className="text-xl font-bold block uppercase tracking-wide text-blueGray-600">
                    22
                  </span>
                  <span className="text-sm text-blueGray-400">Listings</span>
                </div>
                <div className="mr-4 p-3 text-center">
                  <span className="text-xl font-bold block uppercase tracking-wide text-blueGray-600">
                    {count}
                  </span>
                  <span className="text-sm text-blueGray-400">Vehicles</span>
                </div>
                <div className="lg:mr-4 p-3 text-center">
                  <span className="text-xl font-bold block uppercase tracking-wide text-blueGray-600">
                    {count}
                  </span>
                  <span className="text-sm text-blueGray-400">Booked Spots</span>
                </div>
              </div>
            </div>
          </div>
          <div className="text-center mt-12">
            <h3 className="text-xl font-semibold leading-normal mb-2 text-blueGray-700 mb-2">
              {user.username}
            </h3>
            <div className="text-sm leading-normal mt-0 mb-2 text-blueGray-400 font-bold uppercase">
              <i className="fas fa-map-marker-alt mr-2 text-lg text-blueGray-400"></i>{" "}
              {user.contactEmail}
            </div>
            <div className="mb-2 text-blueGray-600 mt-10">
              Contact Number: {user.contactPhoneNumber}
            </div>
           
          </div>
          <div className="mt-10 py-10 border-t border-blueGray-200 text-center">
            <div className="flex flex-wrap justify-center">
              <div className="w-full lg:w-9/12 px-4">
                <textarea className="mb-4 text-lg leading-relaxed text-blueGray-700" placeholder="Write anything you want...">
                 
                </textarea>
               
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}


export default ProfileCard;